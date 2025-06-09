package thara.restaurant_pos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import thara.restaurant_pos.dto.OrderDTO;
import thara.restaurant_pos.models.EOrderStatus;
import thara.restaurant_pos.models.Order;
import thara.restaurant_pos.models.OrderItem;
import thara.restaurant_pos.models.User;
import thara.restaurant_pos.repository.OrderRepository;
import thara.restaurant_pos.repository.UserRepository;
import thara.restaurant_pos.services.MapToDTOService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/order")
public class OrderController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MapToDTOService mapToDTOService;
    
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getAllOrder() {
        try {
            List<Order> orders = orderRepository.findAll();
            //stream use list can process individual, map to map order with mapToOrder function, toList is to list
            List<OrderDTO> orderDTOs = orders.stream().map(mapToDTOService::mapToOrderDTO).toList();
            return ResponseEntity.ok(orderDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("\"Error: Could not retrieve menu order." + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getOrderById(@PathVariable Integer orderId) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Order not found.");
            }
            Order order = orderOptional.get();
            OrderDTO dto = mapToDTOService.mapToOrderDTO(order);
            return ResponseEntity.ok(dto);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error : Counld not retrieve order. " + e.getMessage());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> createOrder(Authentication authentication, @RequestBody Order requestOrder) {
        try {
            String username = authentication.getName();
            Optional<User> userOptional = userRepository.findByUsername(username);
            
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(401).body("Error: User not found.");
            }

            Order order = new Order();
            order.setUser(userOptional.get());
            order.setDiningTable(requestOrder.getDiningTable());
            order.setStatus(EOrderStatus.ORDER_STATUS_PENDING);
            order.setTotalPrice(requestOrder.getTotalPrice());
            order.setOrderItems(requestOrder.getOrderItems());

            // Set the parent order in each OrderItem
            if (order.getOrderItems() != null) {
                for (OrderItem item : order.getOrderItems()) {
                    item.setOrder(order);
                }
            }

            orderRepository.save(order);

            return ResponseEntity.ok("Order created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
