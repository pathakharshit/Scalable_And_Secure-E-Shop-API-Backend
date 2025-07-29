package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.dto.request.LoginRequest;
import com.major_project.e_commerce.dto.response.JwtResponse;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.security.jwt.JwtUtils;
import com.major_project.e_commerce.security.user.ShopUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtUtils.generateTokenFromUser(authentication);
        ShopUserDetails userDetails = (ShopUserDetails) authentication.getPrincipal();
        JwtResponse response = new JwtResponse(userDetails.getId(),jwtToken);
        return ResponseEntity.ok(ApiResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("Login successfully!!")
                        .data(response)
                .build());
    }
}
