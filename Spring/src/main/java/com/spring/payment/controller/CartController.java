package com.spring.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    	try {
        cartService.addToCart(addToCartRequest, token);
        return ResponseEntity.ok("Product added to cart successfully!");
    	} catch (RuntimeException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	}
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
    
    // 예외 핸들러 분리예정
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(
        		HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage()
        		);
    }
}
