package com.major_project.e_commerce.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    //private String password;
    private CartResponse cart;
    private List<OrderResponse> orders;
}
