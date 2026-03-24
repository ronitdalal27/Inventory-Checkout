package com.example.productInventory.service;

import com.example.productInventory.dto.InventoryDTO;
import com.example.productInventory.entity.Inventory;
import com.example.productInventory.entity.Product;
import com.example.productInventory.exception.InventoryNotFoundException;
import com.example.productInventory.exception.ProductNotFoundException;
import com.example.productInventory.repository.InventoryRepository;
import com.example.productInventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;


    public Inventory addStock(InventoryDTO dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + dto.getProductId()));

        Inventory inventory = inventoryRepository
                .findByProductId(product.getId())
                .orElse(new Inventory());

        inventory.setProduct(product);
        inventory.setQuantity(inventory.getQuantity() + dto.getQuantity());

        return inventoryRepository.save(inventory);
    }
}