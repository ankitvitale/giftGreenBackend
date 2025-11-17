package com.giftGreenEcom.Controller;

import com.giftGreenEcom.DTO.AddressDto.ShippingAddressDto;
import com.giftGreenEcom.Service.ShippingAddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/Address")
public class ShippingAddressController {

    @Autowired
    private ShippingAddressService shippingAddressService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> addShippingAddress(@RequestBody ShippingAddressDto addressDto,
                                                @
                                                        AuthenticationPrincipal UserDetails userDetails) {
        try {
            return ResponseEntity.ok(
                    shippingAddressService.addShippingAddress(addressDto, userDetails.getUsername())
            );
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> getById(@PathVariable Long id,
                                     @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(shippingAddressService.getById(id, user.getUsername()));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @RequestBody ShippingAddressDto dto,
                                    @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(shippingAddressService.updateAddress(id, dto, user.getUsername()));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(shippingAddressService.deleteAddress(id, user.getUsername()));
    }
}
