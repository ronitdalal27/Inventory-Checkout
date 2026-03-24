package com.example.productInventory.service;

import com.example.productInventory.dto.CheckoutResponseDTO;
import com.example.productInventory.entity.Inventory;
import com.example.productInventory.entity.Product;
import com.example.productInventory.exception.EmptyCartException;
import com.example.productInventory.exception.InventoryNotFoundException;
import com.example.productInventory.exception.OutOfStockException;
import com.example.productInventory.exception.ProductNotFoundException;
import com.example.productInventory.repository.InventoryRepository;
import com.example.productInventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CartService cartService;
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;

    public CheckoutResponseDTO checkout() {

        if(cartService.viewCart().isEmpty()){
            throw new EmptyCartException("Cart is Empty !");
        }

        double total = 0;

        for (Map.Entry<Integer, Integer> entry : cartService.viewCart().entrySet()) {

            int productId = entry.getKey();
            int qty = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

            Inventory inventory = inventoryRepository
                    .findByProductId(productId)
                    .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id " + productId));

            if (inventory.getQuantity() < qty) {
                throw new OutOfStockException(product.getName() + " is out of stock . Available qty is " + qty);
            }

            inventory.setQuantity(inventory.getQuantity() - qty);
            inventoryRepository.save(inventory);

            total += product.getPrice() * qty;
        }

        cartService.clearCart();

        CheckoutResponseDTO response = new CheckoutResponseDTO();
        response.setTotalAmount(total);
        response.setMessage("Checkout successful");

        return response;
    }
}