package com.example.productInventory.controller;

import com.example.productInventory.dto.CheckoutResponseDTO;
import com.example.productInventory.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponseDTO checkout() {
        return checkoutService.checkout();
    }
}