package com.major_project.e_commerce.service.cart;

import com.major_project.e_commerce.dto.response.CartResponse;
import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.User;

import java.math.BigDecimal;

public interface CartService {
    CartResponse getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    CartResponse covertToDtoResponse(Cart cart);

    Cart convertToModel(CartResponse cartResponse);
}
