package com.major_project.e_commerce.aop;

import com.major_project.e_commerce.exception.AlreadyExistException;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.exception.SecurityException;
import com.major_project.e_commerce.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.builder()
                        .message(ex.getMessage())
                        .httpStatus(HttpStatus.NOT_FOUND.value())
                        .build());
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExistException(AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.builder()
                        .message(ex.getMessage())
                        .httpStatus(HttpStatus.CONFLICT.value())
                        .build());
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiResponse> handleJwtException(SecurityException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                .message((ex.getMessage()))
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.builder()
                .message("You do not have permission for this action!!")
                .httpStatus(HttpStatus.FORBIDDEN.value())
                .build());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiResponse> handleJwtException(JwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                .message("You may login and try again!!")
                .httpStatus(HttpStatus.UNAUTHORIZED.value())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                .message(ex.getMessage())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build());
    }
}
