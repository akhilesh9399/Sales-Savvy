package com.example.Sales.Savvy.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
            @Id
            @Column(name = "order_id")
            private String orderId;
            @Column(name = "user_id", nullable = false)
          private int userId;
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    @Enumerated (EnumType.STRING) @Column(name = "status", nullable = false)
    private OrderStatus status;
    @Column(name = "created_at", nullable  =false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name  = "updated_at")
    private LocalDateTime updatedAt;
    @OneToMany (mappedBy =  "order", cascade =  CascadeType.ALL, fetch =  FetchType.LAZY)
    private List<OrderItem> orderItems;

}
