package io.app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Orders {
    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",nullable = false)
    private User user;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Set<OrderItem> orderItems=new HashSet<>();
    @Column(nullable = false)
    private double totalPrice;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status=OrderStatus.PROCESSING;
    @Column(nullable = false)
    private String shippedAddress;
}
