package io.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.app.dto.ApiResponse;
import io.app.dto.ProductDto;
import io.app.models.Product;
import io.app.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl service;

    @PostMapping("/create-product")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createProduct(@RequestParam(name = "product") String product,
                                     @RequestParam("image") MultipartFile image) throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        Product productValue=objectMapper.readValue(product,Product.class);
        return service.createProduct(productValue,image);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long id) throws IOException {
        return service.getProductById(id);
    }

    @GetMapping("/all-products")
    @ResponseStatus(HttpStatus.OK)
    public Page<Product> getProducts(
            @RequestParam(name = "category",required = false) String category,
            @RequestParam int pageNo){
        return service.getProducts(pageNo,category);
    }

}








