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

    // add product
    public ProductDTO addProduct(ProductDTO productDTO) {
        if(productDTO.getPrice() <= 0){
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        // DTO to Entity
        Product product = modelMapper.map(productDTO, Product.class);

        // Save to DB
        Product savedProduct = productRepository.save(product);

        // Entity to DTO
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