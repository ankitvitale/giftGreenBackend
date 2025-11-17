package com.giftGreenEcom.DTO.ProductDto;

import com.giftGreenEcom.DTO.ProductDto.VariantDto.VariantResponseDTO;

import java.util.List;

    public class ProductResponseDTO {
        private Long id;
        private String name;
        private String description;
        private String category;
        private String productType;
        private String terrariumType;
        private String pickupLocation;
        private List<VariantResponseDTO> variants;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public String getProductType() { return productType; }
        public void setProductType(String productType) { this.productType = productType; }

        public String getTerrariumType() { return terrariumType; }
        public void setTerrariumType(String terrariumType) { this.terrariumType = terrariumType; }

        public String getPickupLocation() { return pickupLocation; }
        public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }

        public List<VariantResponseDTO> getVariants() { return variants; }
        public void setVariants(List<VariantResponseDTO> variants) { this.variants = variants; }
    }
