package io.app.service.impl;

import io.app.dto.ApiResponse;
import io.app.dto.CartResponse;
import io.app.dto.OrdersDto;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.Cart;
import io.app.models.Orders;
import io.app.models.OrderItem;
import io.app.models.User;
import io.app.repository.CartRepository;
import io.app.repository.OrderItemRepository;
import io.app.repository.OrderRepository;
import io.app.repository.UserRepository;
import io.app.service.JwtService;
import io.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;


    @Override
    public ApiResponse makeOrder(String token,CartResponse cartResponse,String address) {
        String email=jwtService.extractUsername(token);
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Invalid User Token!"));

        List<Cart> carts=cartRepository.findByUser(user);

        if(carts==null){
            throw new ResourceNotFoundException("Carts are empty!");
        }

        Set<OrderItem> orderItems=cartResponse.getResult().stream().map((cartItem)->{
            OrderItem orderItem=modelMapper.map(cartItem,OrderItem.class);
            orderItem.setId(null);
            return orderItem;
        }).collect(Collectors.toSet());

        String orderGeneratedId=orderGeneratedId();

        Orders order=new Orders();
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setId(UUID.randomUUID().toString());
        order.setTotalPrice(cartResponse.getTotalAmount());
        order.setShippedAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderGeneratedId(orderGeneratedId);
        repository.save(order);

        cartRepository.deleteAll(carts);

        return new ApiResponse("Order Placed Succesfully",true);
    }

    @Override
    public List<OrdersDto> getOrderByUser(String token) {
        String email=jwtService.extractUsername(token);
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new ResourceNotFoundException("Invalid User Token!"));

        List<OrdersDto> result=repository.findByUser(user)
                .stream().map(order-> {
                    return modelMapper.map(order, OrdersDto.class);
                })
                .sorted(Comparator.comparing(OrdersDto::getOrderDate))
                .collect(Collectors.toList());

        result.stream().sorted();

        return result;
    }

    public String orderGeneratedId(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random=new SecureRandom();
        StringBuilder id=new StringBuilder();
        for (int i=0;i<10;i++){
            int index=random.nextInt(CHARACTERS.length());
            id.append(CHARACTERS.charAt(index));
        }
        return id.toString();
    }
}
