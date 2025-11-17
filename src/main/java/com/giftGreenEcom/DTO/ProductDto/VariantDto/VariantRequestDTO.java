package com.giftGreenEcom.DTO.ProductDto.VariantDto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class VariantRequestDTO {
    private String color;
    private double price;
    private int qty;
    private String size;
    private List<MultipartFile> images;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}