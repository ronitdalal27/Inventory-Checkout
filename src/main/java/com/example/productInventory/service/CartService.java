package com.example.productInventory.service;

import com.example.productInventory.dto.CartDTO;
import com.example.productInventory.exception.ProductNotFoundException;
import com.example.productInventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {

    // ✅ Use productId instead of Product object
    private final Map<Integer, Integer> cart = new HashMap<>();

    private final ProductRepository productRepository;

    public Map<Integer, Integer> addToCart(CartDTO dto) {

        if(dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity, Quantity should be greater than 0");
        }

        productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + dto.getProductId()));

        cart.put(dto.getProductId(),
                cart.getOrDefault(dto.getProductId(), 0) + dto.getQuantity());

        return cart;
    }

    public Map<Integer, Integer> viewCart() {
        return cart;
    }

    public void clearCart() {
        cart.clear();
    }
}