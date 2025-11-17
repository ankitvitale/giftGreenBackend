package com.giftGreenEcom.Controller;

import com.giftGreenEcom.DTO.CartDto.CartDtoResponse;
import com.giftGreenEcom.Service.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> addTocart(@RequestParam Long id,
                                       @RequestParam int quantity,
                                       @AuthenticationPrincipal UserDetails userDetails){
        try {
            return ResponseEntity.ok(cartService.addToCart(id, quantity, userDetails.getUsername()));
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<?> removeItem(
            @RequestParam Long variantId,
            @AuthenticationPrincipal UserDetails userDetails) {

        cartService.removeItemFromCart(variantId, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "Item removed successfully"));
    }



    @GetMapping("/view")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<CartDtoResponse> viewCart(@AuthenticationPrincipal UserDetails userDetails) {
        CartDtoResponse response = cartService.viewCart(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/updateQuantity")
    @PreAuthorize("hasRole('User')")
    public ResponseEntity<CartDtoResponse> updateCartQuantity(
            @RequestParam Long variantId,
            @RequestParam int quantity,
            @AuthenticationPrincipal UserDetails userDetails) {

        CartDtoResponse response = cartService.updateCartQuantity(variantId, quantity, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }


}
