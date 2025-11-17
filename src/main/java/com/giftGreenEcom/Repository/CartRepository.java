package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.Cart;
import com.giftGreenEcom.Entity.ShippingAddress;
import com.giftGreenEcom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUser(User user);

//    List<Cart> findByShippingAddress(ShippingAddress address);

    Optional<Cart> findByShippingAddress(ShippingAddress shippingAddress);

}