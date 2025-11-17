package com.giftGreenEcom.Mapper;

import com.giftGreenEcom.DTO.ProductDto.VariantDto.VariantResponseDTO;
import com.giftGreenEcom.Entity.Variant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface VariantMapper {

    @Mapping(target = "discountedPrice", ignore = true)   // you will set it manually
    @Mapping(source = "images", target = "images")        // use custom mapper
    VariantResponseDTO toVariantResponseDto(Variant variant);

    // Convert List<byte[]> -> List<String Base64>
    default List<String> map(List<byte[]> images) {
        if (images == null) return null;

        return images.stream()
                .map(img -> Base64.getEncoder().encodeToString(img))
                .collect(Collectors.toList());
    }
}
