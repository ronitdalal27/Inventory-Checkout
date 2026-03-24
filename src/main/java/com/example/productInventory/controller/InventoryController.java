package com.example.productInventory.controller;

import com.example.productInventory.dto.InventoryDTO;
import com.example.productInventory.entity.Inventory;
import com.example.productInventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public Inventory addStock(@Valid @RequestBody InventoryDTO dto) {
        return inventoryService.addStock(dto);
    }
}
