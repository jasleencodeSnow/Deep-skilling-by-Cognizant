package com.cognizant.order.service;

import com.cognizant.order.client.UserClient;
import com.cognizant.order.model.Order;
import com.cognizant.order.model.UserDto;
import com.cognizant.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserClient userClient; // OpenFeign client -> user-service

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        // Validate that the user placing the order actually exists (inter-service call)
        UserDto user = userClient.getUserById(order.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found with id " + order.getUserId());
        }
        return orderRepository.save(order);
    }

    // Returns order + the user who placed it, composed from both microservices
    public Map<String, Object> getOrderWithUser(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));
        UserDto user = userClient.getUserById(order.getUserId());
        return Map.of("order", order, "user", user);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
