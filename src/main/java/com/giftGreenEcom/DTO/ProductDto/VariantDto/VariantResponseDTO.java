package com.giftGreenEcom.DTO.ProductDto.VariantDto;

import java.util.List;

public class VariantResponseDTO {
    private Long id;
    private String color;
    private double price;
    private double discountedPrice;
    private int qty;
    private String size;
    private List<String> images; // Base64 encoded strings

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}