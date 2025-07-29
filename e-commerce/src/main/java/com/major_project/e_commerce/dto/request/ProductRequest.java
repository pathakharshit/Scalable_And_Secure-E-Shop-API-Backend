package com.major_project.e_commerce.dto.request;

import com.major_project.e_commerce.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;
    private String brand;
    private BigDecimal price;
    private Integer inventory;
    private String description;
    private Category category;
}
