package com.major_project.e_commerce.service.order;

import com.major_project.e_commerce.dto.response.OrderItemResponse;
import com.major_project.e_commerce.dto.response.OrderResponse;
import com.major_project.e_commerce.enums.OrderStatus;
import com.major_project.e_commerce.exception.ResourceNotFoundException;
import com.major_project.e_commerce.model.Cart;
import com.major_project.e_commerce.model.Order;
import com.major_project.e_commerce.model.OrderItem;
import com.major_project.e_commerce.model.Product;
import com.major_project.e_commerce.repository.CartRepository;
import com.major_project.e_commerce.repository.OrderRepository;
import com.major_project.e_commerce.repository.ProductRepository;
import com.major_project.e_commerce.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;


    @Override
    public OrderResponse placeOrder(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItems = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Order saveOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return convertToDto(saveOrder);
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream().map(this::convertToDto).toList();
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getItems().stream()
                .map(cartItem -> {
                    Product product = cartItem.getProduct();
                    if(cartItem.getQuantity() > product.getInventory())
                        throw new RuntimeException("Out of stock!!");
                    product.setInventory(product.getInventory() - cartItem.getQuantity());
                    productRepository.save(product);
                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getUnitPrice())
                            .build();
                }).toList();
    }
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(orderItem -> {
                    return orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()));
                }).reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    @Override
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found!!"));
    }

    @Override
    public OrderResponse convertToDto(Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();
        OrderResponse orderResponse = modelMapper.map(order,OrderResponse.class);
        Set<OrderItemResponse> orderItemResponses = orderItems.stream()
                .map(orderItem -> {
                    return modelMapper.map(orderItem,OrderItemResponse.class);
                }).collect(Collectors.toSet());
        orderResponse.setOrderItems(orderItemResponses);
        return orderResponse;
    }
}
