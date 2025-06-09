package thara.restaurant_pos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import thara.restaurant_pos.models.EPaymentMethod;

public class PaymentDTO {
    private Long Id;
    private BigDecimal amount;
    private EPaymentMethod method;
    private LocalDateTime paid_at;
    private OrderDTO order;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public EPaymentMethod getMethod() {
        return method;
    }

    public void setMethod(EPaymentMethod method) {
        this.method = method;
    }

    public LocalDateTime getPaid_at() {
        return paid_at;
    }

    public void setPaid_at(LocalDateTime paid_at) {
        this.paid_at = paid_at;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
