package io.app.dto;

import io.app.models.Product;
import io.app.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartDto {
    private long id;
    private Product product;
    private String selectedSize;
    private String selectedColor;
}
