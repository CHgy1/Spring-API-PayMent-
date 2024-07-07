package com.spring.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.spring.payment.entity.Cart;
import com.spring.payment.request.AddToCartRequest;
import com.spring.payment.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest addToCartRequest, @RequestHeader("Authorization") String token) {
        cartService.addToCart(addToCartRequest, token);
        return ResponseEntity.ok("Product added to cart successfully!");
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(cartService.getCart(token));
    }

    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token) {
        cartService.clearCart(token);
        return ResponseEntity.ok("Cart cleared successfully!");
    }
}
