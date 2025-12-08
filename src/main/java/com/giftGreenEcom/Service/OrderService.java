package com.giftGreenEcom.Service;


import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;
import com.giftGreenEcom.DTO.PlaceOrderDto.OrderItemRequest;
import com.giftGreenEcom.DTO.PlaceOrderDto.OrderRespond.OrderItemResponseDTO;
import com.giftGreenEcom.DTO.PlaceOrderDto.OrderRespond.OrderResponseDTO;
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

    @Autowired
    private AdminRepository adminRepository;

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

    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc();

        return orders.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setPaymentMethod(order.getPaymentMethod());
            dto.setStatus(order.getStatus());
            dto.setCreatedAt(order.getCreatedAt());
            dto.setRazorpayOrderId(order.getRazorpayOrderId());
            dto.setRazorpayPaymentId(order.getRazorpayPaymentId());
            dto.setRazorpaySignature(order.getRazorpaySignature());
            dto.setUserId(order.getUser().getId());
            dto.setUserName(order.getUser().getName());
            dto.setUserEmail(order.getUser().getEmail());

            // Shipping address
            if(order.getShippingAddress() != null) {
                ShippingAddressDto sa = new ShippingAddressDto();
                sa.setId(order.getShippingAddress().getId());
                sa.setFullName(order.getShippingAddress().getFullName());
                sa.setCity(order.getShippingAddress().getCity());
                sa.setState(order.getShippingAddress().getState());
                sa.setCountry(order.getShippingAddress().getCountry());
                sa.setStreet(order.getShippingAddress().getStreet());
                sa.setZipCode(order.getShippingAddress().getZipCode());
                sa.setPhoneNumber(order.getShippingAddress().getPhoneNumber());
                dto.setShippingAddress(sa);
            }

            // Order items
            List<OrderItemResponseDTO> items = order.getOrderItems().stream().map(item -> {
                OrderItemResponseDTO itemDTO = new OrderItemResponseDTO();
                itemDTO.setId(item.getId());
                itemDTO.setProductId(item.getProduct().getId());
                itemDTO.setVariantId(item.getVariant().getId());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());
                itemDTO.setDiscountedPrice(item.getDiscountedPrice());
                return itemDTO;
            }).toList();

            dto.setOrderItems(items);

            return dto;
        }).toList();
    }

    public Order cancelOrder(Long orderId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        Admin admin= adminRepository.findByEmail(email);
        boolean isAdmin = admin.getRole().stream()
                .anyMatch(role -> role.getRoleName().equals("Admin"));

        // If not admin → user can cancel only own order
        if (!isAdmin && !order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not allowed to cancel this order");
        }

        // Only pending orders can be cancelled
        if (!order.getStatus().equals("PENDING")) {
            throw new RuntimeException("Only PENDING orders can be cancelled");
        }

        // Restore stock
        for (OrderItem item : order.getOrderItems()) {
            Variant variant = item.getVariant();
            variant.setQty(variant.getQty() + item.getQuantity());
            variantRepository.save(variant);
        }

        order.setStatus("CANCELLED");
        return orderRepository.save(order);
    }


}
