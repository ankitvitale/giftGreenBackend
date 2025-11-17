package com.giftGreenEcom.Service;


import com.giftGreenEcom.DTO.PlaceOrderDto.OrderItemRequest;
import com.giftGreenEcom.DTO.PlaceOrderDto.PlaceOrderRequest;
import com.giftGreenEcom.Entity.*;
import com.giftGreenEcom.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private  CartRepository cartRepository;

    public Order placeOrder(String email, PlaceOrderRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ShippingAddress shippingAddress = shippingAddressRepository
                .findById(request.getShippingAddressId())
                .orElseThrow(() -> new RuntimeException("Shipping Address not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart has no items");
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(shippingAddress);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setStatus("PENDING");

        List<OrderItem> orderItemList = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 🔥 Convert CartItems → OrderItems
        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setVariant(cartItem.getVariant());

            // quantity
            orderItem.setQuantity(cartItem.getQuantity());

            // original price
            orderItem.setPrice(cartItem.getVariant().getPrice());

            // discounted price (already calculated in cart)
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            orderItemList.add(orderItem);

            // Add to total (discounted total)
            total = total.add(BigDecimal.valueOf(cartItem.getDiscountedPrice()));
        }

        order.setOrderItems(orderItemList);
        order.setTotalAmount(total);

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear cart after placing order
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public List<Order> getOrdersByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUser(user);
    }

}
