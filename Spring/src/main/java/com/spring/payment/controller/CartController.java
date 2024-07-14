package com.spring.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.payment.domain.Cart;
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
        return ResponseEntity.ok("장바구니 담기 완료");
    	} catch (RuntimeException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	}
    }
    
    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestHeader("Authorization") String token) {
    	Cart cart = cartService.getCart(token);
        return ResponseEntity.ok(cartService.getCart(token));
    }
    
    @PostMapping("/clear")
    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token) {
        cartService.clearCart(token);
        return ResponseEntity.ok("Clear Cart");
    }
    

}
