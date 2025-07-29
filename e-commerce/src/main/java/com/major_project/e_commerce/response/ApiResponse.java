package com.major_project.e_commerce.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiResponse {
    private String message;
    private Integer httpStatus;
    private Object data;
}
