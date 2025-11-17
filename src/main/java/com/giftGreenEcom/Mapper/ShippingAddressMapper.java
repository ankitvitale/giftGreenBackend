package com.giftGreenEcom.Mapper;

import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;
import com.giftGreenEcom.Entity.ShippingAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShippingAddressMapper {

    ShippingAddressDto toDto(ShippingAddress address);

    @Mapping(target = "id", ignore = true) // ID auto-generated
    ShippingAddress toEntity(ShippingAddressDto dto);
}
