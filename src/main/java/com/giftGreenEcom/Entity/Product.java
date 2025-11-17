package com.giftGreenEcom.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giftGreenEcom.Entity.Enumration.ProductType;
import com.giftGreenEcom.Entity.Enumration.TerrariumType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String category;

    @Enumerated(EnumType.STRING)
    private ProductType productType; // SIMPLE or TERRARIUM

    @Enumerated(EnumType.STRING)
    private TerrariumType terrariumType; // SINGLE or KIT

    private String pickupLocation;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Variant> variants = new ArrayList<>();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public ProductType getProductType() { return productType; }
    public void setProductType(ProductType productType) { this.productType = productType; }

    public TerrariumType getTerrariumType() { return terrariumType; }
    public void setTerrariumType(TerrariumType terrariumType) { this.terrariumType = terrariumType; }

    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

    public List<Variant> getVariants() { return variants; }
    public void setVariants(List<Variant> variants) { this.variants = variants; }
}