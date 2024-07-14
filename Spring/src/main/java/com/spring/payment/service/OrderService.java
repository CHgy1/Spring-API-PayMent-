package com.spring.payment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payment.domain.Cart;
import com.spring.payment.domain.Order;
import com.spring.payment.domain.OrderItem;
import com.spring.payment.domain.Product;
import com.spring.payment.domain.User;
import com.spring.payment.repository.CartItemRepository;
import com.spring.payment.repository.CartRepository;
import com.spring.payment.repository.OrderRepository;
import com.spring.payment.repository.ProductRepository;
import com.spring.util.DateUtil;
import com.spring.util.ParserToken;

@Service
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
	private ParserToken parserToken;
    
    private DateUtil dateUtil;
    public Order createOrder(String token) {
        // 토큰에서 사용자 이름을 추출하고 사용자 엔티티를 조회
        User user = parserToken.conversionToToken(token);

        // 사용자의 장바구니를 조회
        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다.");
        }

        // 새로운 주문을 생성하고 사용자 및 상태 설정
        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreated_At(dateUtil.dateFormatter());

        // 장바구니 아이템들을 주문 아이템으로 변환
        List<OrderItem> orderItems = cart.getCartItems().stream()
            .map(cartItem -> {
                Product product = cartItem.getProduct();

                if (product.getStock() == 0) {
                    throw new RuntimeException("상품 " + product.getProductName() + "의 재고 없음");
                }

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setStock(cartItem.getStock());
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);

                // 재고 업데이트
                product.setStock(product.getStock() - cartItem.getStock());
                productRepository.save(product);
                
                return orderItem;
                
            }).collect(Collectors.toList());

        // 주문에 주문 아이템 및 총 가격 설정
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getStock()).sum());

        // 주문을 저장
        Order savedOrder = orderRepository.save(order);

        // 장바구니 아이템들을 삭제하고 장바구니 초기화
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }
}
