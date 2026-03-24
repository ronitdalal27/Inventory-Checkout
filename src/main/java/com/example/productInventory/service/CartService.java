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
    git branch - to check on which branch you are on currently
    to switch the branch - git checkout branch_name

    now suppose we want to implement product validation feature so we will cut a branch from this commited work i.e. copy
    git checkout -b feature/product-validation
    this branch will get created and then we will switch to this created branch automatically(feature/product-inventory)

    once you create a specific branch suppose feature/product-validation
    then u first need to git add ., git commit -m "message", then git push origin feature/product-validation then created feature branch is uptodate
    then if once feature is completed so we can merge this created branch with main/master branch

    to do this first u need to
    git checkout master - switch to master/main branch i.e. with which branch u need to merge this feature branch, so we want to merge with main/master branch
    option 1 to merge - dirct option
    git merge feature/product-validation(branch-name) now master branch has all the code of feautre/product-validation branch

    now we create merge-conflict


 */