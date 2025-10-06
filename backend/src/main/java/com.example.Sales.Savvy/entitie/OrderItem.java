package com.example.Sales.Savvy.entitie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
        @Id
        @GeneratedValue(strategy = GenerationType. IDENTITY)
        private int id;
        @ManyToOne(fetch =  FetchType.LAZY)
        @JoinColumn(name = "order_id", nullable = false)
        private Order order;
        @Column(name = "product_id", nullable = false)
        private int productId;
        @Column(name = "quantity", nullable =  false)
        private int quantity;
        @Column(name =  "price_per_unit", nullable =  false)
        private BigDecimal pricePerUnit;
        @Column(name =  "total_price", nullable = false)
        private BigDecimal totalPrice;
}
