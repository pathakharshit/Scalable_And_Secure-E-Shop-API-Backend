package com.major_project.e_commerce.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
}
