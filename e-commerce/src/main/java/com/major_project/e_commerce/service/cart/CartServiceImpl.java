package com.major_project.e_commerce.service.cart;

import com.major_project.e_commerce.dto.response.*;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.*;
import com.major_project.e_commerce.repository.CartItemRepository;
import com.major_project.e_commerce.repository.CartRepository;
import com.major_project.e_commerce.repository.UserRepository;
import com.major_project.e_commerce.service.product.ProductService;
import com.major_project.e_commerce.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Override
    public CartResponse getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!!"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return covertToDtoResponse(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found!!"));
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = convertToModel(getCart(id));
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartResponse covertToDtoResponse(Cart cart) {
        Set<CartItem> cartItems =  cart.getItems();
        Set<CartItemResponse> cartItemResponseList = cartItems.stream()
                .map(item -> {
                    Product product = item.getProduct();
                    ProductResponse productResponse = modelMapper.map(product,ProductResponse.class);
                    //cartItemResponse.setProductResponse(productResponse);
                    return modelMapper.map(item,CartItemResponse.class);
                }).collect(Collectors.toSet());
        CartResponse cartResponse = modelMapper.map(cart,CartResponse.class);
        cartResponse.setCartItems(cartItemResponseList);
        return cartResponse;
    }

    @Override
    public Cart convertToModel(CartResponse cartResponse) {
        Set<CartItem> cartItems = cartResponse.getCartItems()
                .stream().map(cartItemResponse -> {
                    return modelMapper.map(cartItemResponse,CartItem.class);
                    //ProductResponse productResponse = cartItemResponse.getProductResponse();
                    //Product product = productService.convertToModel(productResponse);
                    //cartItem.setProduct(product);
                    //return cartItem;
                }).collect(Collectors.toSet());

        Cart cart = modelMapper.map(cartResponse,Cart.class);
        cart.setItems(cartItems);
        return cart;
    }
}
