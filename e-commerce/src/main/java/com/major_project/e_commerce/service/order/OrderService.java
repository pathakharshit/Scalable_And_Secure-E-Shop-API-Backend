package com.major_project.e_commerce.service.order;

import com.major_project.e_commerce.dto.response.OrderResponse;
import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.Order;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(Long userId);

    List<OrderResponse> getUserOrders(Long userId);

    OrderResponse getOrder(Long orderId);

    OrderResponse convertToDto(Order order);
}
