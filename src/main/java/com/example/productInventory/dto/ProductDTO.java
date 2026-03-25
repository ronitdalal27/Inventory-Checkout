package com.example.productInventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private int id;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "price cannot be blank")
    @Positive(message = "Price must be greater than 0")
    private Integer price;
}
