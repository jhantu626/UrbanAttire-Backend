package io.app.dto;

import io.app.models.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Set<String> colors=new HashSet<>();
    private Set<String> sizes=new HashSet<>();
    private Double price; // Price of the product
    private int stockQuantity;
    private Category category;
}





