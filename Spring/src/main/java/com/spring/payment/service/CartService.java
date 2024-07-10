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

import jakarta.transaction.Transactional;

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

    /**
     * 장바구니에 아이템을 추가하는 메서드
     * 
     * @param addToCartRequest 장바구니에 추가할 아이템 정보
     * @param token 사용자 인증 토큰
     * @return 업데이트된 장바구니
     */
    @Transactional
    public Cart addToCart(AddToCartRequest addToCartRequest, String token) {
        // 토큰에서 사용자 이름을 추출하고 사용자 엔티티를 조회
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        
        // 사용자의 장바구니를 조회, 없으면 새로 생성
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        // 추가할 상품을 조회, 없으면 예외 발생
        Product product = productRepository.findById(addToCartRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // 재고가 없는 경우 예외 발생
        if (product.getStock() == 0) {
            throw new RuntimeException("상품 재고 없음");
        }
        
        // 장바구니에 이미 해당 상품이 있는지 확인
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProduct().getId().equals(product.getId())) {
                // 상품이 이미 장바구니에 있으면 추가하지 않고 장바구니 반환
                return cart;
            }
        }

        // 장바구니에 없는 상품이면 새로 추가
        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setQuantity(addToCartRequest.getQuantity());
        newCartItem.setCart(cart);

        newCartItem = cartItemRepository.save(newCartItem);

        cart.getCartItems().add(newCartItem);

        return cartRepository.save(cart);
    }

    /**
     * 사용자의 장바구니를 조회하는 메서드
     * 
     * @param token 사용자 인증 토큰
     * @return 조회된 장바구니
     */
    public Cart getCart(String token) {
        // 토큰에서 사용자 이름을 추출하고 사용자 엔티티를 조회
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart != null) {
            // 장바구니의 각 아이템에 대해 상품 정보를 강제로 로드
            cart.getCartItems().forEach(cartItem -> {
                cartItem.getProduct().getName(); // Force fetch product
            });
        }
        return cart;
    }

    /**
     * 사용자의 장바구니를 비우는 메서드
     * 
     * @param token 사용자 인증 토큰
     */
    public void clearCart(String token) {
        // 토큰에서 사용자 이름을 추출하고 사용자 엔티티를 조회
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));
        Cart cart = cartRepository.findByUserId(user.getId());
        // 장바구니 아이템들을 모두 삭제하고 장바구니를 비움
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }


}
