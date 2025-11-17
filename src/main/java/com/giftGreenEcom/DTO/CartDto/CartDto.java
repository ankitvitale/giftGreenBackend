package com.giftGreenEcom.DTO.CartDto;

import java.util.List;

public class CartDto {
    private Long cartId;
    private String userEmail;
    private List<CartItemDto> cartItems;
    // getters & setters


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

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }
}