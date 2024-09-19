package io.app.controller;

import io.app.dto.ApiResponse;
import io.app.dto.CartResponse;
import io.app.dto.OrdersDto;
import io.app.models.Orders;
import io.app.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl service;

    @PostMapping("make-order")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ApiResponse placeOrder(@RequestHeader("Authorization") String token,
                                  @RequestBody CartResponse cartResponse,
                                  @RequestParam String address){
        token=token.substring(7);
        return service.makeOrder(token,cartResponse,address);
    }

    @GetMapping("/all-orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersDto> allOrders(@RequestHeader("Authorization") String token){
        token=token.substring(7);
        return service.getOrderByUser(token);
    }

}
