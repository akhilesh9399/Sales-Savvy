package com.example.Sales.Savvy.repo;

import com.example.Sales.Savvy.entitie.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    Optional<Category> findByCategoryName(String categoryName);
}
