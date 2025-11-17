package com.giftGreenEcom.Entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private double price;
    private int qty;
    private String size;

    @ElementCollection
    @CollectionTable(name = "variant_images", joinColumns = @JoinColumn(name = "variant_id"))
    @Column(name = "image_data", columnDefinition = "LONGBLOB")
    private List<byte[]> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQty() { return qty; }
    public void setQty(int qty) { this.qty = qty; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public List<byte[]> getImages() { return images; }
    public void setImages(List<byte[]> images) { this.images = images; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}