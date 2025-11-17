package com.giftGreenEcom.Service;


import com.giftGreenEcom.DTO.CartDto.CartDtoResponse;
import com.giftGreenEcom.Entity.*;
import com.giftGreenEcom.Mapper.CartMapper;
import com.giftGreenEcom.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BannerRepository bannerRepository;

    public CartDtoResponse addToCart(Long variantId, int quantity, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

        Product product = variant.getProduct();

        // validate stock
        if (quantity > variant.getQty()) {
            throw new IllegalArgumentException("Requested quantity exceeds available stock.");
        }

        // find existing cart item for this variant
        CartItem cartItem = (CartItem) cartItemRepository.findByCartAndVariant(cart, variant).orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVariant(variant);
            cartItem.setProduct(product);
            cartItem.setQuantity(0);
        }

        int totalQuantity = cartItem.getQuantity() + quantity;
        if (totalQuantity > variant.getQty()) {
            throw new IllegalArgumentException("Total quantity in cart exceeds available stock.");
        }

        // apply discount from banner (if any for product category)
        Optional<Banner> bannerOpt = bannerRepository.findByCategoryIgnoreCase(product.getCategory());
        double discount = bannerOpt.map(Banner::getDiscount).orElse(0.0);

        double discountedUnitPrice = calculateDiscountedPrice(variant.getPrice(), discount);

        cartItem.setQuantity(totalQuantity);
        cartItem.setDiscount(discount);
        cartItem.setDiscountedPrice(discountedUnitPrice * totalQuantity);

        cart.addCartItem(cartItem);
        cartItemRepository.save(cartItem);

        return cartMapper.toDtoResponse(cart);
    }

    private double calculateDiscountedPrice(double price, double discount) {
        return price - (price * discount / 100.0);
    }

    public CartDtoResponse viewCart(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart is empty"));

        return cartMapper.toDtoResponse(cart);
    }

    public void removeItemFromCart(Long variantId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

        CartItem cartItem = (CartItem) cartItemRepository.findByCartAndVariant(cart, variant)
                .orElseThrow(() -> new EntityNotFoundException("Item not found in cart"));

        // Remove item from cart
        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);

        // Save updated cart
        cartRepository.save(cart);
    }

    public CartDtoResponse updateCartQuantity(Long variantId, int quantity, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new EntityNotFoundException("Variant not found"));

        CartItem cartItem = (CartItem) cartItemRepository.findByCartAndVariant(cart, variant)
                .orElse(null);

        // NEW QUANTITY
        int updatedQty = (cartItem == null ? 0 : cartItem.getQuantity()) + quantity;

        // ❌ Negative quantity & no item – invalid
        if (cartItem == null && updatedQty <= 0) {
            throw new IllegalArgumentException("Invalid quantity update.");
        }

        // ❌ Quantity exceeds stock
        if (updatedQty > variant.getQty()) {
            throw new IllegalArgumentException("Total quantity exceeds available stock.");
        }

        // 🗑 REMOVE item if final quantity <= 0
        if (updatedQty <= 0) {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.delete(cartItem);
            return cartMapper.toDtoResponse(cart);
        }

        // 🟢 UPDATE or CREATE cart item
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setVariant(variant);
            cartItem.setProduct(variant.getProduct());
        }

        // Get banner discount
        Optional<Banner> bannerOpt =
                bannerRepository.findByCategoryIgnoreCase(variant.getProduct().getCategory());

        double discount = bannerOpt.map(Banner::getDiscount).orElse(0.0);
        double discountedUnitPrice = calculateDiscountedPrice(variant.getPrice(), discount);

        cartItem.setQuantity(updatedQty);
        cartItem.setDiscount(discount);
        cartItem.setDiscountedPrice(updatedQty * discountedUnitPrice);

        cartItemRepository.save(cartItem);

        return cartMapper.toDtoResponse(cart);
    }

}
