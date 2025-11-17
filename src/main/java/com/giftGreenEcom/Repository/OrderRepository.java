package com.giftGreenEcom.Repository;

import com.giftGreenEcom.Entity.Order;
import com.giftGreenEcom.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
