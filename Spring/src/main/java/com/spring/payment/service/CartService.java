package com.spring.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payment.entity.Cart;
import com.spring.payment.entity.CartItem;
import com.spring.payment.entity.Product;
import com.spring.payment.repository.CartRepository;
import com.spring.payment.repository.ProductRepository;
import com.spring.payment.repository.CartItemRepository;
import com.spring.user.entity.UserEntity;
import com.spring.user.jwt.JwtUtil;
import com.spring.user.repository.UserRepository;
import com.spring.payment.request.AddToCartRequest;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Cart addToCart(AddToCartRequest addToCartRequest, String token) {
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));

        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        Product product = productRepository.findById(addToCartRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(addToCartRequest.getQuantity());
        cartItem.setCart(cart);

        cartItem = cartItemRepository.save(cartItem);

        cart.getCartItems().add(cartItem);

        return cartRepository.save(cart);
    }

    public Cart getCart(String token) {
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            cart.getCartItems().forEach(cartItem -> {
                cartItem.getProduct().getName(); // Force fetch product
            });
        }
        return cart;
    }

    public void clearCart(String token) {
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        Cart cart = cartRepository.findByUserId(user.getId());
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
