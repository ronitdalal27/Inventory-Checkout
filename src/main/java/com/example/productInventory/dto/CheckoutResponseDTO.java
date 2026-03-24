package com.example.productInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class CheckoutResponseDTO {
    @PositiveOrZero(message = "Total amount cannot be negative")
    private double totalAmount;

    @NotBlank(message = "Message cannot be empty")
    private String message;
}
