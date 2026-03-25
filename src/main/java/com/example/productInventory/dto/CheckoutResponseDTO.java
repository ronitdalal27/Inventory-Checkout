package com.example.productInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CheckoutResponseDTO {
    private double totalAmount;
    private String message;
}
