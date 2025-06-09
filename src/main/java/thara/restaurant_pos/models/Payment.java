package thara.restaurant_pos.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "method", nullable = false)
    @Enumerated(EnumType.STRING)
    private EPaymentMethod method;

    @Column(name = "paid_at", nullable = false, updatable = false)
    private LocalDateTime paid_at = LocalDateTime.now();

    public Payment() {}

    public Payment(Long id, Order order, BigDecimal amount, EPaymentMethod method, LocalDateTime paid_at) {
        this.id = id;
        this.order = order;
        this.amount = amount;
        this.method = method;
        this.paid_at = paid_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
    
}
