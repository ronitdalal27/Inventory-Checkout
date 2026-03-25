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

        //if user has not added anything to cart so he will see this exception
        if(cartService.viewCart().isEmpty()){
            throw new EmptyCartException("Cart is Empty !");
        }

        //to calculate entire total
        double total = 0;

        //fetch one on one entry from cart like <1-10> product-id,quantity
        for (Map.Entry<Integer, Integer> entry : cartService.viewCart().entrySet()) {

            int productId = entry.getKey();
            int qty = entry.getValue();

            //if the specific product is not available so through productnotfoundexception
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id " + productId));

            //check how much stock of particulra product is available
            Inventory inventory = inventoryRepository
                    .findByProductId(productId)
                    .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id " + productId));

            //if less stock is present than added more stock in cart so throw the desired exception
            if (inventory.getQuantity() < qty) {
                throw new OutOfStockException(product.getName() + " is out of stock . Available qty is " + inventory.getQuantity());
            }

            //reduce stock of product from inventory and save the final inventory
            inventory.setQuantity(inventory.getQuantity() - qty);
            inventoryRepository.save(inventory);

            //claculate total
            total += product.getPrice() * qty;
        }

        //at the end clear cart
        cartService.clearCart();

        //so as we are returning checkoutresponse we will just simply create the object of it set total n message n donee
        CheckoutResponseDTO response = new CheckoutResponseDTO();
        response.setTotalAmount(total);
        response.setMessage("Checkout successful");

        return response;
    }
}