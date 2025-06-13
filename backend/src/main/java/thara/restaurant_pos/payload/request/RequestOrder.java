package thara.restaurant_pos.payload.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import thara.restaurant_pos.models.OrderItem;

public class RequestOrder {

    private Long tableId;

    @NotBlank
    private List<OrderItem> orderItems;

    public RequestOrder() {

    }

    public RequestOrder(Long tableId, List<OrderItem> orderItems) {
        this.tableId = tableId;
        this.orderItems = orderItems;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}
