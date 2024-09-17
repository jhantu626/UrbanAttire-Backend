package io.app.service;

import io.app.dto.ApiResponse;
import io.app.dto.ProductDto;
import io.app.models.Category;
import io.app.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    public ApiResponse createProduct(Product product, MultipartFile image) throws IOException;
    public Product getProductById(Long id) throws IOException;
    public Page<Product> getProducts(int page,String category);
}
