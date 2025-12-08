package com.giftGreenEcom.Controller;

import com.giftGreenEcom.DTO.PlaceOrderDto.OrderHistoryItemResponse;
import com.giftGreenEcom.DTO.PlaceOrderDto.OrderHistoryResponse;
import com.giftGreenEcom.DTO.PlaceOrderDto.OrderRespond.OrderResponseDTO;
import com.giftGreenEcom.DTO.PlaceOrderDto.PlaceOrderRequest;
import com.giftGreenEcom.Entity.Order;
import com.giftGreenEcom.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> placeOrder(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody PlaceOrderRequest request) {

        Order order = orderService.placeOrder(userDetails.getUsername(), request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/my-orders")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<List<OrderHistoryResponse>> getUserOrderHistory(
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername(); // current logged-in user email

        List<Order> orders = orderService.getOrdersByUser(email);

        List<OrderHistoryResponse> response = orders.stream().map(order ->
                new OrderHistoryResponse(
                        order.getId(),
                        order.getTotalAmount(),
                        order.getPaymentMethod(),
                        order.getStatus(),
                        order.getCreatedAt(),
                        order.getOrderItems().stream().map(item ->
                                new OrderHistoryItemResponse(
                                        item.getId(),
                                        item.getProduct().getId(),
                                        item.getProduct().getName(),
                                        item.getVariant().getId(),
                                        item.getVariant().getColor(),
                                        item.getQuantity(),
                                        item.getDiscountedPrice(),
                                        item.getVariant().getImages()
                                )
                        ).toList()
                )
        ).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("hasAnyRole('User','Admin')")
    public ResponseEntity<?> cancelOrder(
            @PathVariable Long orderId,
            @AuthenticationPrincipal UserDetails userDetails) {

        Order cancelledOrder = orderService.cancelOrder(orderId, userDetails.getUsername());
        return ResponseEntity.ok(cancelledOrder);
    }



}
