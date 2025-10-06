package com.example.Sales.Savvy.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP")
    private LocalDateTime createdAt;
    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT TIMESTAMP ON UPDATE CURPENT TIMESTAMP")
    private LocalDateTime updatedAt;
}
