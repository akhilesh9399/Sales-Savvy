package com.example.Sales.Savvy.controller;

import com.example.Sales.Savvy.entitie.Product;
import com.example.Sales.Savvy.entitie.User;
import com.example.Sales.Savvy.repo.CartRepository;
import com.example.Sales.Savvy.repo.ProductRepository;
import com.example.Sales.Savvy.repo.UserRepository;
import com.example.Sales.Savvy.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/add")
    @CrossOrigin(origins = "http://localhost:5174", allowCredentials = "true")
    public ResponseEntity<Void> addToCart(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        int productId = (int) request.get("productId");
// Handle quantity: Default to 1 if not provided
        int quantity = request.containsKey("quantity") ? (int) request.get("quantity") : 1;
// Fetch the user using username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User with user not present"));
        cartService.addToCart(user.getUserId(), productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/items/count")
    public ResponseEntity<Integer> getCartItems(@RequestParam String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("UserWith user " + username + " not found"));
        int count = cartService.getCartItemCount(user.getUserId());
        return ResponseEntity.ok(count);
    }


    // Fetch all cart items for the user (based on username)
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getCartItems(HttpServletRequest request) {
// Fetch user by username to get the userId
        User user = (User) request.getAttribute("authenticatedUser");
// Call the service to get cart items for the user
        Map<String, Object> cartItems = cartService.getCartItems(user.getUserId());
        return ResponseEntity.ok(cartItems);
    }

    @PutMapping("/update")

    public ResponseEntity<Void> updateCartItemQuantity(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        int productId = (int) request.get("productId");
        int quantity = (int) request.get("quantity");
// Fetch the user using username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new
                IllegalArgumentException("User not found with username:" + username));
// Update the cart item quantity
        cartService.updateCartItemQuantity(user.getUserId(), productId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCartItem(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        int productId = (int) request.get("productId");
// Fetch the user using username
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found with username:" + username));
// Delete the cart item
        cartService.deleteCartItem(user.getUserId(), productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}