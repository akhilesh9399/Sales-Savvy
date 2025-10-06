package com.example.Sales.Savvy.repo;


import com.example.Sales.Savvy.entitie.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

// Custom query methods can be added here if needed

}