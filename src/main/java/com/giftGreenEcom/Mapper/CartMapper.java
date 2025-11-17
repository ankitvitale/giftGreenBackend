package com.giftGreenEcom.Mapper;


import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;
import com.giftGreenEcom.DTO.CartDto.CartDtoResponse;
import com.giftGreenEcom.DTO.CartDto.CartItemDto;
import com.giftGreenEcom.DTO.CartDto.CartItemResponse;
import com.giftGreenEcom.Entity.Cart;
import com.giftGreenEcom.Entity.CartItem;
import com.giftGreenEcom.Entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "cartId", source = "cart.id")
    @Mapping(target = "userEmail", source = "cart.user.email")
    @Mapping(target = "cartItems", expression = "java(mapCartItems(cart.getCartItems()))")
    @Mapping(target = "totalPrice", expression = "java(calculateTotal(cart.getCartItems()))")
    CartDtoResponse toDtoResponse(Cart cart);

    // Convert CartItem -> CartItemResponse
    default List<CartItemResponse> mapCartItems(List<CartItem> items) {
        return items.stream().map(item -> {

            CartItemResponse dto = new CartItemResponse();

            dto.setVariantId(item.getVariant().getId());
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setColor(item.getVariant().getColor());
            dto.setSize(item.getVariant().getSize());
            dto.setQuantity(item.getQuantity());
            dto.setPrice(item.getVariant().getPrice());
            dto.setDiscount(item.getDiscount());
            dto.setDiscountedPrice(item.getDiscountedPrice());

            // ✔ FIXED: Return raw byte[] image list
            dto.setImages(item.getVariant().getImages());

            return dto;

        }).toList();
    }
    // 🔹 Map ShippingAddress -> ShippingAddressDto
    default ShippingAddressDto mapShipping(ShippingAddress address) {
        if (address == null) return null;

        ShippingAddressDto dto = new ShippingAddressDto();
        dto.setId(address.getId());
        dto.setFullName(address.getFullName());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setCountry(address.getCountry());
        dto.setPhoneNumber(address.getPhoneNumber());

        return dto;
    }
    default double calculateTotal(List<CartItem> items) {
        return items.stream()
                .mapToDouble(CartItem::getDiscountedPrice)
                .sum();
    }
}
