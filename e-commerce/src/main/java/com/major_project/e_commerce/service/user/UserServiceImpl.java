package com.major_project.e_commerce.service.user;

import com.major_project.e_commerce.dto.request.CreateUserRequest;
import com.major_project.e_commerce.dto.request.UpdateUserRequest;
import com.major_project.e_commerce.dto.response.CartResponse;
import com.major_project.e_commerce.dto.response.OrderResponse;
import com.major_project.e_commerce.dto.response.UserResponse;
import com.major_project.e_commerce.enums.UserType;
import com.major_project.e_commerce.exception.AlreadyExistException;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.Role;
import com.major_project.e_commerce.model.User;
import com.major_project.e_commerce.repository.RoleRepository;
import com.major_project.e_commerce.repository.UserRepository;
import com.major_project.e_commerce.service.cart.CartService;
import com.major_project.e_commerce.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse getUserById(Long userId) {
        return userRepository.findById(userId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        try {
            UserType userType = UserType.fromString(request.getUserType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Role userRole = roleRepository.findByName(request.getUserType()).get();

        return Optional.of(request)
                .filter(user ->
                        !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = User.builder()
                            .email(req.getEmail())
                            .firstName(req.getFirstName())
                            .lastName(req.getLastName())
                            .password(passwordEncoder.encode(req.getPassword()))
                            .roles(Set.of(userRole))
                            .build();
                    return convertToDto(userRepository.save(user));
                }).orElseThrow(() -> new AlreadyExistException("User already exist!!"));
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return convertToDto(userRepository.save(existingUser));
                }).orElseThrow(() -> new ResourceNotFoundException("User not found!!"));
    }

    @Override
    public User getAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,
                () -> {throw new ResourceNotFoundException("User not found");});
    }

    @Override
    public UserResponse convertToDto(User user) {
        Cart cart = user.getCart();

        CartResponse cartResponse = null;
        if(cart != null)
            cartResponse = cartService.covertToDtoResponse(cart);
        List<OrderResponse> orderResponses = orderService.getUserOrders(user.getId());
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        userResponse.setCart(cartResponse);
        userResponse.setOrders(orderResponses);
        return userResponse;
    }
}
