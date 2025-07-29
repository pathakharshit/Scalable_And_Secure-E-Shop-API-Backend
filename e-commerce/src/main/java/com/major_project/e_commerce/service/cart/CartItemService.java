package com.major_project.e_commerce.service.cart;

import com.major_project.e_commerce.dto.response.CartItemResponse;
import com.major_project.e_commerce.model.CartItem;

public interface CartItemService {
    void addItemToCart(Long cartId,Long productId,Integer quantity);
    void removeItemToCart(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,Integer quantity);
}
