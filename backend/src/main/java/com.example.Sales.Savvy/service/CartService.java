package com.example.Sales.Savvy.service;

import com.example.Sales.Savvy.entitie.CartItem;
import com.example.Sales.Savvy.entitie.Product;
import com.example.Sales.Savvy.entitie.ProductImage;
import com.example.Sales.Savvy.entitie.User;
import com.example.Sales.Savvy.repo.CartRepository;
import com.example.Sales.Savvy.repo.ProductImageRepository;
import com.example.Sales.Savvy.repo.ProductRepository;
import com.example.Sales.Savvy.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ProductImageRepository productImageRepository;


    public void addToCart(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User net found with 10: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with 10: " + productId));

// Fetch cart item for this userid and productid
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(userId, productId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartRepository.save(cartItem);

        } else {
            CartItem newItem = new CartItem(user, product, quantity);
            cartRepository.save(newItem);
        }
    }

    public int getCartItemCount(int userId) {
        return cartRepository.getTotalCount(userId);
    }

    // Get cart items for a User
    public Map<String, Object> getCartItems(int userId) {
// Fetch the cart items for the user with product details
        List<CartItem> cartItems = cartRepository.findCartItemsWithProductDetails(userId);
// Create a response map to hold the cart details

        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        response.put("username", user.getUsername());
        response.put("role", user.getRole().toString());

        // List to hold the product details
        List<Map<String, Object>> products = new ArrayList<>();
        int overallTotalPrice = 0;
        for (CartItem cartItem : cartItems) {
            Map<String, Object> productDetails = new HashMap<>();
            // Get product details
            Product product = cartItem.getProduct();
            // Fetch product images
            List<ProductImage> productImages = productImageRepository.findByProduct_ProductId(product.getProductId());
            String imageurl = (productImages != null && !productImages.isEmpty()) ? productImages.get(0).getImageUrl() : "default-image-url";
            // Populate product details
            productDetails.put("product id", product.getProductId());
            productDetails.put("image_uri", imageurl);
            productDetails.put("name", product.getName());
            productDetails.put("description", product.getDescription());
            productDetails.put("price_per_unit", product.getPrice());
            productDetails.put("quantity", cartItem.getQuantity());
            productDetails.put("total_price", cartItem.getQuantity() * product.getPrice().doubleValue());
            //Add to products list
            products.add(productDetails);
            // Update overall total price
            overallTotalPrice += cartItem.getQuantity() * product.getPrice().doubleValue();
        }
        // Prepare the final cart response
        Map<String, Object> cart = new HashMap<>();
        cart.put("products", products);
        cart.put("overall_total_price", overallTotalPrice);
        response.put("cart", cart);

        return response;

    }

    // update
    public void updateCartItemQuantity(int userId, int productId, int quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

// Fetch cart item for this userId and productId
        Optional<CartItem> existingItem = cartRepository.findByUserAndProduct(userId, productId);
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            if (quantity == 0) {
               deleteCartItem(userId, productId);
            } else {
                cartItem.setQuantity(quantity);
                cartRepository.save(cartItem);

            }
        }
    }
    //delete cart
    public void deleteCartItem(int userId, int productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        cartRepository.deleteCartItem(userId, productId);

    }

}