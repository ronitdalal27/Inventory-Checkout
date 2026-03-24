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


/*
    git branch - shows all branch
                * shows current branch

    to switch branch - git checkout branch_name
    for eg git checkout feature/cart-service

    to create new branch
    git checkout -b feature/product-validation
    this will create new branch and switch to it, now as we make changes in this code we do git add . and then commit
    and then push - git push origin feature/product-validation
    now this branch is available on github

    merge branch -
    always merge features into main branch
    therefore switch to main branch - git checkout main
    then do -  git merge feature/product-validation
    now our main branch has feature branch code

    what is merge conflict ?
    when changes in same line, same file, changes from both branches occures
    so we need to raise the pr manually and then see what to accep and what to not so we can see at that time

    if our master branch is updated, but feature branch does not have that code so
    1st option - git checkout feature/product-validation
                 git merge main
    2nd option(preferred) - git checkout feature/product-validation
                            git rebase main

    to delete branch - git branch -d feature/product-validation








 */