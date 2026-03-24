package com.example.productInventory.service;

import com.example.productInventory.dto.ProductDTO;
import com.example.productInventory.entity.Product;
import com.example.productInventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    //add product
    public ProductDTO addProduct(ProductDTO productDTO) {

        //print message
        System.out.println("master change");

        System.out.println("cart-service change");

        // Validation
        if (productDTO.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }

        // Normalize name (IMPORTANT)
        String normalizedName = productDTO.getName().toLowerCase().trim();

        // Check if product already exists
        Product existingProduct = productRepository
                .findByName(normalizedName)
                .orElse(null);

        if (existingProduct != null) {
            // Update existing product price
            existingProduct.setPrice(productDTO.getPrice());

            Product updatedProduct = productRepository.save(existingProduct);

            return modelMapper.map(updatedProduct, ProductDTO.class);
        }

        // Create new product
        Product product = modelMapper.map(productDTO, Product.class);
        product.setName(normalizedName);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    //get all products
    public List<ProductDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }
}