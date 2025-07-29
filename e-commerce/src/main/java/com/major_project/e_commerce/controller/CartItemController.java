package com.major_project.e_commerce.controller;

import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.User;
import com.major_project.e_commerce.repository.UserRepository;
import com.major_project.e_commerce.response.ApiResponse;
import com.major_project.e_commerce.service.cart.CartItemService;
import com.major_project.e_commerce.service.cart.CartService;
import com.major_project.e_commerce.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart/cart-items")
public class CartItemController {
    private final CartItemService cartItemService;
    private final CartService cartService;
    private  final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
          User user = userService.getAuthenticated();
          Cart cart = cartService.initializeNewCart(user);
          cartItemService.addItemToCart(cart.getId(),productId,quantity);
          return ResponseEntity.ok(ApiResponse.builder()
                .message("Item added to cart!!")
                .httpStatus(HttpStatus.OK.value())
                .data("Your card ID number : " + cart.getId())
                .build());
    }

    @DeleteMapping("/{cartId}/product/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long productId) {
        cartItemService.removeItemToCart(cartId,productId);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Item removed from cart!!")
                .httpStatus(HttpStatus.OK.value())
                .build());
    }

    @PutMapping("/{cartId}/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,@PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        cartItemService.updateItemQuantity(cartId,productId,quantity);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Quantity updated!!")
                .httpStatus(HttpStatus.OK.value())
                .build());
    }
}
