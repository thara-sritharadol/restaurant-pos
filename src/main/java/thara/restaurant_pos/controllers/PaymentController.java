package thara.restaurant_pos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import thara.restaurant_pos.dto.OrderDTO;
import thara.restaurant_pos.dto.PaymentDTO;
import thara.restaurant_pos.models.Order;
import thara.restaurant_pos.models.Payment;
import thara.restaurant_pos.payload.request.RequestPayment;
import thara.restaurant_pos.repository.OrderRepository;
import thara.restaurant_pos.repository.PaymentRepository;
import thara.restaurant_pos.services.MapToDTOService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {
    
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MapToDTOService mapToDTOService;

    public PaymentDTO mapToPaymentDTO(Payment payment, OrderDTO orderDTO) {
        return null;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getAllPayment() {
        try {
            List<Payment> payments = paymentRepository.findAll();
            List<PaymentDTO> paymentDTOs = payments.stream()
            .map(payment -> mapToDTOService.mapToPaymentDTO(payment, payment.getOrder())).toList();
            return ResponseEntity.ok(paymentDTOs);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> findPaymentById(@PathVariable Long paymentId) {
        try {
            Optional<Payment> paymentOptional = paymentRepository.findById(paymentId);

            if (paymentOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Payment not found.");
            }

            Payment payment = paymentOptional.get();
            PaymentDTO paymentDTO = mapToDTOService.mapToPaymentDTO(payment, payment.getOrder());
            return ResponseEntity.ok(paymentDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error" + e.getMessage());
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> createPayment(@Valid @RequestBody RequestPayment requestPayment) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(requestPayment.getOrderId());
            if (orderOptional.isEmpty()) {
                return ResponseEntity.status(404).body("Order not found.");
            }

            Order order = orderOptional.get();

            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalPrice());
            payment.setMethod(requestPayment.getMethod());

            paymentRepository.save(payment);

            return ResponseEntity.ok().body("Payment created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error :" + e.getMessage());
        }
    }

}
