package thara.restaurant_pos.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Integer id;
    private Integer menuItemId;
    private Integer quantity;
    private BigDecimal price;
    private String note;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getMenuItemId() { return menuItemId; }
    public void setMenuItemId(Integer menuItemId) { this.menuItemId = menuItemId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    
}
