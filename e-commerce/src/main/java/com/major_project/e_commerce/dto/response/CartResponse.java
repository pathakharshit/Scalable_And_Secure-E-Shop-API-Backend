package com.major_project.e_commerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CartResponse {
    private Long cartId;
    private BigDecimal totalAmount = BigDecimal.ZERO;;
    private Set<CartItemResponse> cartItems;
}
