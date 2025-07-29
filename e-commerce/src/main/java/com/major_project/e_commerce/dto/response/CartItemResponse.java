package com.major_project.e_commerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private String productName;
    private String productBrand;
    private String productDescription;
    //private ProductResponse productResponse;
}
