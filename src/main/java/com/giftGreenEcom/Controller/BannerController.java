package com.giftGreenEcom.Controller;

import com.giftGreenEcom.Entity.Banner;
import com.giftGreenEcom.Entity.Enumration.DiscountType;
import com.giftGreenEcom.Repository.BannerRepository;
import com.giftGreenEcom.Service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerRepository bannerRepository;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public Banner addBanner(
            @RequestParam("name") String name,
            @RequestParam("discountType") DiscountType discountType,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam("discount") double discount,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return bannerService.addBanner(name, discountType, category, productId, discount, image);
    }


    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('Admin')")
    public Banner updateBanner(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("discountType") DiscountType discountType,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam("discount") double discount,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {

        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        banner.setName(name);
        banner.setDiscountType(discountType);
        banner.setDiscount(discount);

        // Reset old values
//        banner.setCategory(null);
//        banner.setProductId(null);

        if (discountType == DiscountType.CATEGORY) {
            banner.setCategory(category);
            banner.setProductId(null);
        } else if (discountType == DiscountType.PRODUCT) {
            banner.setProductId(productId);
            banner.setCategory(null);
        }

        if (image != null && !image.isEmpty()) {
            banner.setImage(image.getBytes());
        }

        return bannerRepository.save(banner);
    }
    @GetMapping("/all")
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Banner getBannerById(@PathVariable Long id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void deleteBanner(@PathVariable Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));

        bannerRepository.delete(banner);
    }


}
