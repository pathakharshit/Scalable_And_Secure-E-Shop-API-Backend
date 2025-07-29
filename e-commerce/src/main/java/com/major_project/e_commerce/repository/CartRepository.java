package com.major_project.e_commerce.repository;

import com.major_project.e_commerce.dto.response.CartResponse;
import com.major_project.e_commerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Cart findByUserId(Long userId);
}
