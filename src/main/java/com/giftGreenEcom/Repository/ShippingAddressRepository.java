package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.ShippingAddress;
import com.giftGreenEcom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShippingAddressRepository extends JpaRepository<ShippingAddress,Long> {

    List<ShippingAddress> findByUser(User user);
}
