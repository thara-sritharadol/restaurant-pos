package thara.restaurant_pos.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import thara.restaurant_pos.dto.OrderDTO;
import thara.restaurant_pos.dto.OrderItemDTO;
import thara.restaurant_pos.dto.PaymentDTO;
import thara.restaurant_pos.models.Order;
import thara.restaurant_pos.models.Payment;

@Service
public class MapToDTOService {

        public OrderDTO mapToOrderDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setDiningTableId(order.getDiningTable() != null ? order.getDiningTable().getId() : null);
        dto.setStatus(order.getStatus().name());
        dto.setTotalPrice(order.getTotalPrice());


        dto.setCreatedAt(order.getCreatedAt());

        if (order.getOrderItems() != null) {
            List<OrderItemDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setId(item.getId());
                itemDTO.setMenuItemId(item.getMenuItem() != null ? item.getMenuItem().getId() : null);
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());

                BigDecimal totalPrice = BigDecimal.ZERO;
                itemDTO.setTotalPrice(totalPrice.add(itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()))));

                itemDTO.setNote(item.getNote());
                return itemDTO;
            }).toList();
            dto.setOrderItems(itemDTOs);
        }

        return dto;
    }

    public PaymentDTO mapToPaymentDTO(Payment payment, Order order) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setMethod(payment.getMethod());
        paymentDTO.setPaid_at(payment.getPaid_at());
        paymentDTO.setOrder(mapToOrderDTO(order));
        return paymentDTO;
    }
}
