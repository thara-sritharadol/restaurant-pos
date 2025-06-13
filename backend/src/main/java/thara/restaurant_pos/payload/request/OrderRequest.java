package thara.restaurant_pos.payload.request;

import java.util.List;

import thara.restaurant_pos.models.DiningTable;

public class OrderRequest {
    private DiningTable diningTable;
    private List<OrderItemRequest> orderItems;

    public DiningTable getDiningTable() {
        return diningTable;
    }

    public void setDiningTable(DiningTable diningTable) {
        this.diningTable = diningTable;
    }

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
    

}
