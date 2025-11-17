package com.giftGreenEcom.Mapper;

import com.giftGreenEcom.DTO.ProductDto.ProductResponseDTO;
import com.giftGreenEcom.Entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {VariantMapper.class})
public interface ProductMapper {

    @Mapping(source = "variants", target = "variants")
    ProductResponseDTO toResponseDto(Product product);
}
