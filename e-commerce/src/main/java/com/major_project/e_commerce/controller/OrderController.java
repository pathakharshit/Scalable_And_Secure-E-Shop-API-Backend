package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.response.OrderResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> placeOrders(@RequestParam Long userId) {
        OrderResponse orderResponse = orderService.placeOrder(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                        .data(orderResponse)
                        .httpStatus(HttpStatus.OK.value())
                        .message("Order placed successfully!!")
                .build());
    }

    @GetMapping("/user/{userId}/all-orders")
    public ResponseEntity<ApiResponse> getUsersOrder(@PathVariable Long userId) {
        List<OrderResponse> orderResponses = orderService.getUserOrders(userId);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(orderResponses)
                .httpStatus(HttpStatus.OK.value())
                .message("Your orders : ")
                .build());
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getOrder(orderId);
        return ResponseEntity.ok(ApiResponse.builder()
                .data(orderResponse)
                .httpStatus(HttpStatus.OK.value())
                .message("Order having id : " + orderId)
                .build());
    }
}
