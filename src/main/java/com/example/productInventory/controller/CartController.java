package com.example.productInventory.controller;

import com.example.productInventory.dto.CartDTO;
import com.example.productInventory.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public Map<Integer, Integer> addToCart(@Valid @RequestBody CartDTO dto) {
        return cartService.addToCart(dto);
    }

    @GetMapping
    public Map<Integer, Integer> viewCart() {
        return cartService.viewCart();
    }
}