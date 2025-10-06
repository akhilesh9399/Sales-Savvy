package com.example.Sales.Savvy.repo;

import com.example.Sales.Savvy.entitie.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JWTTokenRepository extends JpaRepository<JWTToken,Integer> {
     Optional<JWTToken> findByToken(String token);


    @Query("SELECT t FROM JWTToken t Where t.user.userId = :userId")
    JWTToken findByUserId(Integer userId);
}
