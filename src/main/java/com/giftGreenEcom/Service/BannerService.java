package com.giftGreenEcom.Service;


import com.giftGreenEcom.DTO.BannerDto.BannerResponseDTO;
import com.giftGreenEcom.Entity.Banner;
import com.giftGreenEcom.Entity.Enumration.DiscountType;
import com.giftGreenEcom.Entity.Product;
import com.giftGreenEcom.Entity.Variant;
import com.giftGreenEcom.Repository.BannerRepository;
import com.giftGreenEcom.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductService productService;

    public Banner addBanner(String name, DiscountType discountType, String category, Long productId,
                            double discount, MultipartFile image) throws IOException {

        Banner banner = new Banner();
        banner.setName(name);
        banner.setDiscountType(discountType);
        banner.setDiscount(discount);

        if (discountType == DiscountType.CATEGORY && category != null) {
            banner.setCategory(category);
            banner.setProductId(null);

        }
        else if (discountType == DiscountType.PRODUCT && productId != null) {
            banner.setProductId(productId);
            banner.setCategory(null);

        }

        // Save image in DB as byte[]
        if (image != null && !image.isEmpty()) {
            banner.setImage(image.getBytes());
        }

        return bannerRepository.save(banner);
    }






}
