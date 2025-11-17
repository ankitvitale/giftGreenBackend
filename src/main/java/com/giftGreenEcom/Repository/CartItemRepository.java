package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.Cart;
import com.giftGreenEcom.Entity.CartItem;
import com.giftGreenEcom.Entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<Object> findByCartAndVariant(Cart cart, Variant variant);
}