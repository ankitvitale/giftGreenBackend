package com.giftGreenEcom.DTO.PlaceOrderDto;

import java.util.List;

public record OrderHistoryItemResponse(
        Long itemId,
        Long productId,
        String productName,
        Long variantId,
        String color,
        int quantity,
        double totalItemPrice,
        List<byte[]> images
) {}
