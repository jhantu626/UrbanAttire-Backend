package io.app.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import io.app.dto.ApiResponse;
import io.app.dto.ProductDto;
import io.app.exceptions.FileSizeExceededException;
import io.app.exceptions.ResourceNotFoundException;
import io.app.models.Category;
import io.app.models.Product;
import io.app.repository.ProductRepository;
import io.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final ModelMapper modelMapper;
    private final Cloudinary cloudinary;
    @Value("${file.product.path}")
    private String productPath;

    @Override
    public ApiResponse createProduct(Product product, MultipartFile image) throws IOException {
        long fileSize=image.getSize();
        int fileSizeInMB=(int)fileSize/(1024*1024);
        if (fileSizeInMB>1){
            throw new FileSizeExceededException("Image size should be <= 1MB!");
        }
        Map uploadResult= cloudinary.uploader().upload(image.getBytes(),
                ObjectUtils.asMap(
                        "transformation",
                        new Transformation<>()
                                .width(800)
                                .height(800)
                                .crop("limit")
                                .quality("auto")
                                .fetchFormat("auto")
                )
        );
        String url=uploadResult.get("url").toString();
        product.setImageUrl(url);
        repository.save(product);
        return new ApiResponse("Product Uploaded Successfully!",true);
    }

    @Override
    public Product getProductById(Long id) throws IOException {
        Product product=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No Such Products are available!"));
        return product;
    }

    @Override
    public Page<Product> getProducts(int page,String category) {
        PageRequest pageRequest=PageRequest.of(page,10);

        Page<Product> productsPage;

        if(category!=null && !category.isEmpty()){
            Category requestCategory=Category.valueOf(category.toUpperCase());
            productsPage=repository.findByCategory(requestCategory,pageRequest);
        }else{
            productsPage=repository.findAll(pageRequest);
        }

        return productsPage;
    }




}
