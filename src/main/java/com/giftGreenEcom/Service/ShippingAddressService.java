package com.giftGreenEcom.Service;

import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;
import com.giftGreenEcom.DTO.CartDto.CartDtoResponse;
import com.giftGreenEcom.Entity.Cart;
import com.giftGreenEcom.Entity.ShippingAddress;
import com.giftGreenEcom.Entity.User;
import com.giftGreenEcom.Mapper.CartMapper;
import com.giftGreenEcom.Mapper.ShippingAddressMapper;
import com.giftGreenEcom.Repository.CartRepository;
import com.giftGreenEcom.Repository.ShippingAddressRepository;
import com.giftGreenEcom.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressService {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShippingAddressMapper shippingAddressMapper;

    @Autowired
    private CartMapper cartMapper;

    public CartDtoResponse addShippingAddress(ShippingAddressDto addressDto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Always get or create cart
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        // Convert DTO → Entity
        ShippingAddress address = shippingAddressMapper.toEntity(addressDto);
        address.setUser(user);

        // Save the new address
        address = shippingAddressRepository.save(address);

        // Attach address to cart
        cart.setShippingAddress(address);
        cartRepository.save(cart);

        // Return updated cart DTO
        return cartMapper.toDtoResponse(cart);
    }


    // GET BY ID
    public ShippingAddressDto getById(Long id, String email) {

        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (!address.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access");
        }

        return shippingAddressMapper.toDto(address);
    }

    // UPDATE ADDRESS
    public ShippingAddressDto updateAddress(Long id, ShippingAddressDto dto, String email) {

        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (!address.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access");
        }

        address.setFullName(dto.getFullName());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        address.setPhoneNumber(dto.getPhoneNumber());

        shippingAddressRepository.save(address);

        return shippingAddressMapper.toDto(address);
    }

    // DELETE ADDRESS
    public String deleteAddress(Long id, String email) {

        ShippingAddress address = shippingAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (!address.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized access");
        }

        // ✔ Check if cart is referencing this address
        Cart cart = cartRepository.findByShippingAddress(address).orElse(null);

        if (cart != null) {
            cart.setShippingAddress(null);
            cartRepository.save(cart);
        }

        // ✔ Now safe to delete
        shippingAddressRepository.delete(address);

        return "Address deleted successfully";
    }

}
