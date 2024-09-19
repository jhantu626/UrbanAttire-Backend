package io.app.service;

import io.app.dto.ApiResponse;
import io.app.dto.CartResponse;
import io.app.dto.OrdersDto;
import io.app.models.Orders;

import java.util.List;

public interface OrderService {
    ApiResponse makeOrder(String token,CartResponse cartResponse,String address);
    List<OrdersDto> getOrderByUser(String token);
}
