package io.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private Set<String> colors=new HashSet<>();
    @Column(nullable = false)
    private Set<String> sizes=new HashSet<>();
    @Column(nullable = false)
    private Double price; // Price of the product
    private int stockQuantity;
    @Enumerated(EnumType.STRING)
    private Category category;
}
