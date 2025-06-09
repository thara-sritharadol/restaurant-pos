package thara.restaurant_pos.services;

import java.util.List;

import org.springframework.stereotype.Service;

import thara.restaurant_pos.dto.OrderDTO;
import thara.restaurant_pos.dto.OrderItemDTO;
import thara.restaurant_pos.models.Order;

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
                itemDTO.setNote(item.getNote());
                return itemDTO;
            }).toList();
            dto.setOrderItems(itemDTOs);
        }

        return dto;
    }
}
