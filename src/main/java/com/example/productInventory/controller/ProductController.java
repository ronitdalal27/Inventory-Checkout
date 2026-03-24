package com.example.productInventory.controller;

import com.example.productInventory.dto.ProductDTO;
import com.example.productInventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ProductDTO addProduct(@Valid @RequestBody ProductDTO product) {
        return  productService.addProduct(product);
    }

    @GetMapping("/get")
    public List<ProductDTO> getProducts() {
        return productService.getAllProducts();
    }
}
