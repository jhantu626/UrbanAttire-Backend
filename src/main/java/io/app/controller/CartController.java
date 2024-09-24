package io.app.controller;

import io.app.dto.ApiResponse;
import io.app.dto.CartDto;
import io.app.dto.CartResponse;
import io.app.models.Cart;
import io.app.service.impl.CartServiceImpl;
import io.app.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartServiceImpl service;


    @PostMapping("/add-cart/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addCart(@RequestHeader("Authorization") String token,
                               @PathVariable("productId") int productId,
                               @RequestParam("size") String size,
                               @RequestParam("color") String color){
        token=token.substring(7);
        return service.addCart(token,(long)productId,size,color);
    }

    @GetMapping("/all-carts")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse getCartsOfUser(@RequestHeader("Authorization") String token){
        token=token.substring(7);
        return service.getCartsByUser(token);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse deleteCart(@PathVariable("id") Long id){
        return service.cartDelete(id);
    }

}









