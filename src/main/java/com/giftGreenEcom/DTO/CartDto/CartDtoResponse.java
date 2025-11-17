package com.giftGreenEcom.DTO.CartDto;

import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;

import java.util.List;

public class CartDtoResponse {
    private Long cartId;
    private String userEmail;
    private List<CartItemResponse> cartItems;

    private ShippingAddressDto shippingAddress;
    private double totalPrice;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public List<CartItemResponse> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemResponse> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ShippingAddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}