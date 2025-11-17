package com.giftGreenEcom.DTO.PlaceOrderDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public record OrderHistoryResponse(
        Long orderId,
        BigDecimal totalAmount,
        String paymentMethod,
        String status,
        Date createdAt,
        List<OrderHistoryItemResponse> items
) {}
