package com.major_project.e_commerce.service.cart;

import com.major_project.e_commerce.dto.response.ProductResponse;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.CartItem;
import com.major_project.e_commerce.repository.CartItemRepository;
import com.major_project.e_commerce.repository.CartRepository;
import com.major_project.e_commerce.service.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void addItemToCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        ProductResponse product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(productService.convertToModel(product));
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void removeItemToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(() -> new ResourceNotFoundException("Item not found!!"));
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().ifPresentOrElse(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();
                },() -> {throw new ResourceNotFoundException("No Item Found");});
        cart.updateTotalAmount();
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
}
