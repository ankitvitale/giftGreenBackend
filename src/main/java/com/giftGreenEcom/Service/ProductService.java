package com.giftGreenEcom.Service;


import com.giftGreenEcom.DTO.ProductDto.ProductRequestDTO;
import com.giftGreenEcom.DTO.ProductDto.ProductResponseDTO;
import com.giftGreenEcom.DTO.ProductDto.VariantDto.VariantRequestDTO;
import com.giftGreenEcom.DTO.ProductDto.VariantDto.VariantResponseDTO;
import com.giftGreenEcom.Entity.Banner;
import com.giftGreenEcom.Entity.Product;
import com.giftGreenEcom.Entity.Variant;
import com.giftGreenEcom.Mapper.ProductMapper;
import com.giftGreenEcom.Repository.BannerRepository;
import com.giftGreenEcom.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private  ProductMapper productMapper;
    @Autowired
    private  BannerRepository bannerRepository;

    public void addProduct(ProductRequestDTO dto) throws IOException {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setProductType(dto.getProductType());
        product.setTerrariumType(dto.getTerrariumType());
        product.setPickupLocation(dto.getPickupLocation());

        List<Variant> variantList = new ArrayList<>();

        if (dto.getVariants() != null && !dto.getVariants().isEmpty()) {
            for (VariantRequestDTO v : dto.getVariants()) {
                Variant variant = new Variant();
                variant.setColor(v.getColor());
                variant.setPrice(v.getPrice());
                variant.setQty(v.getQty());
                variant.setSize(v.getSize());

                List<byte[]> imageData = new ArrayList<>();
                if (v.getImages() != null && !v.getImages().isEmpty()) {
                    for (var img : v.getImages()) {
                        imageData.add(img.getBytes());
                    }
                }
                variant.setImages(imageData);
                variant.setProduct(product);
                variantList.add(variant);
            }
        }

        product.setVariants(variantList);
        productRepository.save(product);
    }

//    public List<ProductResponseDTO> getAllProducts() {
//        List<Product> products = productRepository.findAll();
//
//        return products.stream().map(product -> {
//            ProductResponseDTO dto = new ProductResponseDTO();
//            dto.setId(product.getId());
//            dto.setName(product.getName());
//            dto.setDescription(product.getDescription());
//            dto.setCategory(product.getCategory());
//            dto.setProductType(product.getProductType().name());
//            dto.setTerrariumType(product.getTerrariumType() != null ? product.getTerrariumType().name() : null);
//            dto.setPickupLocation(product.getPickupLocation());
//
//            List<VariantResponseDTO> variantDTOs = product.getVariants().stream().map(variant -> {
//                VariantResponseDTO vdto = new VariantResponseDTO();
//                vdto.setId(variant.getId());
//                vdto.setColor(variant.getColor());
//                vdto.setPrice(variant.getPrice());
//                vdto.setDiscountedPrice(variant.);
//                vdto.setQty(variant.getQty());
//                vdto.setSize(variant.getSize());
//
//                List<String> base64Images = variant.getImages().stream()
//                        .map(img -> Base64.getEncoder().encodeToString(img))
//                        .collect(Collectors.toList());
//                vdto.setImages(base64Images);
//                return vdto;
//            }).collect(Collectors.toList());
//
//            dto.setVariants(variantDTOs);
//            return dto;
//        }).collect(Collectors.toList());
//    }

    public void updateProduct(Long id, ProductRequestDTO dto) throws IOException {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setProductType(dto.getProductType());
        product.setTerrariumType(dto.getTerrariumType());
        product.setPickupLocation(dto.getPickupLocation());

        // ✅ Clear existing variants safely
        List<Variant> existingVariants = product.getVariants();
        existingVariants.clear();

        // ✅ Add new variants to the same list reference
        if (dto.getVariants() != null && !dto.getVariants().isEmpty()) {
            for (VariantRequestDTO v : dto.getVariants()) {
                Variant variant = new Variant();
                variant.setColor(v.getColor());
                variant.setPrice(v.getPrice());
                variant.setQty(v.getQty());
                variant.setSize(v.getSize());

                List<byte[]> imageData = new ArrayList<>();
                if (v.getImages() != null && !v.getImages().isEmpty()) {
                    for (var img : v.getImages()) {
                        imageData.add(img.getBytes());
                    }
                }
                variant.setImages(imageData);
                variant.setProduct(product);

                existingVariants.add(variant); // ✅ add to existing list
            }
        }

        productRepository.save(product);
    }


    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.delete(product);
    }


    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> {

                    double discountPercentage = getDiscountForProductOrCategory(
                            product.getId(),
                            product.getCategory()
                    );

                    ProductResponseDTO dto = productMapper.toResponseDto(product);

                    // Apply discount for each variant
                    dto.getVariants().forEach(variantDto -> {
                        variantDto.setDiscountedPrice(
                                calculateDiscountedPrice(variantDto.getPrice(), discountPercentage)
                        );
                    });

                    return dto;
                })
                .toList();
    }

    // 🔹 1️⃣ Product-wise discount has highest priority
    private double getDiscountForProductOrCategory(Long productId, String category) {

        return bannerRepository.findByProductId(productId)
                .map(Banner::getDiscount)
                .orElseGet(() ->
                        bannerRepository.findByCategoryIgnoreCase(category)
                                .map(Banner::getDiscount)
                                .orElse(0.0)
                );
    }

    // 🔹 2️⃣ Discount calculation
    private double calculateDiscountedPrice(double price, double discountPercentage) {
        return price - (price * discountPercentage / 100);
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setProductType(product.getProductType() != null ? product.getProductType().name() : null);
        dto.setTerrariumType(product.getTerrariumType() != null ? product.getTerrariumType().name() : null);
        dto.setPickupLocation(product.getPickupLocation());

        List<VariantResponseDTO> variantDTOs = product.getVariants().stream().map(variant -> {
            VariantResponseDTO vdto = new VariantResponseDTO();
            vdto.setId(variant.getId());
            vdto.setColor(variant.getColor());
            vdto.setPrice(variant.getPrice());
            vdto.setDiscountedPrice(variant.getPrice());
            vdto.setQty(variant.getQty());
            vdto.setSize(variant.getSize());

            List<String> base64Images = variant.getImages().stream()
                    .map(img -> Base64.getEncoder().encodeToString(img))
                    .collect(Collectors.toList());
            vdto.setImages(base64Images);
            return vdto;
        }).collect(Collectors.toList());

        dto.setVariants(variantDTOs);
        return dto;
    }

}
