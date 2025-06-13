package thara.restaurant_pos.payload.request;

import thara.restaurant_pos.models.EPaymentMethod;

public class RequestPayment {

    private Integer orderId;
    private EPaymentMethod method;

    public RequestPayment() {}

    public RequestPayment(Integer orderId, EPaymentMethod method) {
        this.orderId = orderId;
        this.method = method;
    }

    public Integer getOrderId () { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public EPaymentMethod getMethod() { return method; }
    public void setMethod(EPaymentMethod method) { this.method = method; }
    
}
