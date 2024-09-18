package io.app.service.impl;

import io.app.dto.ApiResponse;
import io.app.dto.CartDto;
import io.app.dto.CartResponse;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.Cart;
import io.app.models.Product;
import io.app.models.User;
import io.app.repository.CartRepository;
import io.app.repository.ProductRepository;
import io.app.repository.UserRepository;
import io.app.service.CartService;
import io.app.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository repository;
    private final JwtService jwtService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse addCart(String token, Long productId,
                               String size,String color) {
        String email=jwtService.extractUsername(token);
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Token"));
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Product Id"));
        Cart haveCart=repository.findByUserAndProduct(user,product);
        if(haveCart!=null){
            return new ApiResponse("Product Already in Cart!",false);
        }
        Cart cart=new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setSelectedSize(size);
        cart.setSelectedColor(color);
        repository.save(cart);
        return new ApiResponse("Cart Added Successfully",true);
    }

    @Override
    public CartResponse getCartsByUser(String token) {
        String email=jwtService.extractUsername(token);
        User user=userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourceNotFoundException("Invalid Token"));
        AtomicReference<Double> paybleAmount=new AtomicReference<>(0.0);
        List<CartDto> results=repository.findByUser(user)
                .stream().map(cart->{
                    paybleAmount.updateAndGet(v->v+cart.getProduct().getPrice());
                    return modelMapper.map(cart,CartDto.class);
                })
                .collect(Collectors.toList());
        double totalAmount=paybleAmount.get();
        log.info("totalAmount: {}",totalAmount);

        return new CartResponse(results,totalAmount);
    }

    @Override
    public ApiResponse cartDelete(Long cartId) {
        Cart cart=repository.findById(cartId)
                .orElseThrow(()->new ResourceNotFoundException("Invalid cart!"));
        repository.delete(cart);

        return new ApiResponse("Cart deleted successfully",true);
    }
}
