package com.giftGreenEcom.DTO.ProductDto;

import com.giftGreenEcom.DTO.ProductDto.VariantDto.VariantRequestDTO;
import com.giftGreenEcom.Entity.Enumration.ProductType;
import com.giftGreenEcom.Entity.Enumration.TerrariumType;

import java.util.List;

public class ProductRequestDTO {
    private String name;
    private String description;
    private String category;
    private ProductType productType;
    private TerrariumType terrariumType;
    private String pickupLocation;
    private List<VariantRequestDTO> variants;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public TerrariumType getTerrariumType() {
        return terrariumType;
    }

    public void setTerrariumType(TerrariumType terrariumType) {
        this.terrariumType = terrariumType;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public List<VariantRequestDTO> getVariants() {
        return variants;
    }

    public void setVariants(List<VariantRequestDTO> variants) {
        this.variants = variants;
    }
}