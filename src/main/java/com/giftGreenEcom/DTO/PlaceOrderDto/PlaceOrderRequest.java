package com.giftGreenEcom.DTO.PlaceOrderDto;

import java.util.List;

public class PlaceOrderRequest {

    private Long shippingAddressId;
    private String paymentMethod;

    // Optional – If ordering directly without cart
    private List<OrderItemRequest> items;

    // Getter & Setter


    public Long getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Long shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
