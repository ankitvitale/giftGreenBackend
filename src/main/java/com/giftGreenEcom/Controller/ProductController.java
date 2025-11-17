package com.giftGreenEcom.Controller;


import com.giftGreenEcom.DTO.ProductDto.ProductRequestDTO;
import com.giftGreenEcom.DTO.ProductDto.ProductResponseDTO;
import com.giftGreenEcom.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

        @PostMapping("/add")
        @PreAuthorize("hasRole('Admin')")
        public ResponseEntity<?> addProduct(@ModelAttribute ProductRequestDTO dto) throws IOException {
            productService.addProduct(dto);
            return ResponseEntity.ok("Product added successfully");
        }

//    @GetMapping("/all")
//    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
//        List<ProductResponseDTO> products = productService.getAllProducts();
//        return ResponseEntity.ok(products);
//    }
@GetMapping("/all")
public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
    return ResponseEntity.ok(productService.getAllProducts());
}


    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @ModelAttribute ProductRequestDTO dto) throws IOException {
        productService.updateProduct(id, dto);
        return ResponseEntity.ok("Product updated successfully");
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }


}
