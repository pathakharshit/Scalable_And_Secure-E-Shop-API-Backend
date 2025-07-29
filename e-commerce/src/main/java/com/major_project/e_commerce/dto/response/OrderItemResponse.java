package com.major_project.e_commerce.dto.response;

import com.major_project.e_commerce.model.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private String productName;
    private String productBrand;
    private String productDescription;
    //private Product product;
}
