package com.spring.payment.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.payment.entity.Order;
import com.spring.payment.entity.OrderItem;
import com.spring.payment.entity.Product;
import com.spring.payment.entity.Cart;
import com.spring.payment.entity.CartItem;
import com.spring.payment.repository.OrderRepository;
import com.spring.payment.repository.ProductRepository;
import com.spring.payment.repository.CartRepository;
import com.spring.user.entity.UserEntity;
import com.spring.user.jwt.JwtUtil;
import com.spring.user.repository.UserRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Order createOrder(String token) {
        UserEntity user = userRepository.findByUsername(jwtUtil.getUsername(token.substring(7)));

        Cart cart = cartRepository.findByUserId(user.getId());
        if (cart == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = cart.getCartItems().stream()
            .map(cartItem -> {
                Product product = cartItem.getProduct();

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(product.getPrice());
                orderItem.setOrder(order);

                product.setStock(product.getStock() - cartItem.getQuantity()); // Update stock
                productRepository.save(product);

                return orderItem;
            }).toList();

        order.setOrderItems(orderItems);
        order.setTotalPrice(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());

        Order savedOrder = orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }
}