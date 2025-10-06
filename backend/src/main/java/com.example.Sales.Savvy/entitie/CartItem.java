package com.example.Sales.Savvy.entitie;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name  = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @Column
    private int quantity;

    public CartItem() {
    }

    public CartItem( User user, Product product, int quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

}
