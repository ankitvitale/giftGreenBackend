package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BannerRepository extends JpaRepository<Banner, Long> {
    Optional<Banner> findByProductId(Long productId);

    // 2️⃣ Find banner with category-based discount (case insensitive)
    Optional<Banner> findByCategoryIgnoreCase(String category);
}
