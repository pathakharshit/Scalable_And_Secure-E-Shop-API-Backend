package com.major_project.e_commerce.dto.response;

import com.major_project.e_commerce.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderResponse {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemResponse> orderItems;
}
