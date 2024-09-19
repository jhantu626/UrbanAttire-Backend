package io.app.dto;

import io.app.models.OrderItem;
import io.app.models.OrderStatus;
import io.app.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrdersDto {
    private String id;
    private Set<OrderItem> orderItems=new HashSet<>();
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private String shippedAddress;
}
