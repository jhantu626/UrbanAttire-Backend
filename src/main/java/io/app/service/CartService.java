package io.app.service;

import io.app.dto.ApiResponse;
import io.app.dto.CartDto;
import io.app.dto.CartResponse;
import io.app.models.Cart;

import java.util.List;

public interface CartService {
    public ApiResponse addCart(String token,Long productId,String size,String color);
    public CartResponse getCartsByUser(String token);
    public ApiResponse cartDelete(Long cartId);
}
