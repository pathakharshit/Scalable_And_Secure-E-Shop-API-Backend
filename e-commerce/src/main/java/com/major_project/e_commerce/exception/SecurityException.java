package com.major_project.e_commerce.exception;

import io.jsonwebtoken.JwtException;

public class SecurityException extends JwtException {
    public SecurityException(String message) {
        super(message);
    }
}
