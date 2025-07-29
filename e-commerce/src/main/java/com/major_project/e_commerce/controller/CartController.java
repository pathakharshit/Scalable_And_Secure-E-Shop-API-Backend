package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.response.CartResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart")
public class CartController {
    public final CartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long id) {
        CartResponse cartResponse = cartService.getCart(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Cart having ID : " + id)
                .httpStatus(HttpStatus.OK.value())
                .data(cartResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long id) {
        cartService.clearCart(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Cart deleted successfully!!")
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @GetMapping("/total-cart-price/{id}")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long id) {
        BigDecimal totalAmount = cartService.getTotalPrice(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Total Price : ")
                .httpStatus(HttpStatus.OK.value())
                .data(totalAmount)
                .build());
    }
}
