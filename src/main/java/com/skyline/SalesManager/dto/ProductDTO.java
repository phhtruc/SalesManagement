package com.skyline.SalesManager.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDTO{

    @NotBlank(message = "productName must be not blank")
    private String productName;

    @Positive(message = "price must be greater than 0")
    private Double price;

    @NotBlank(message = "description must be not blank")
    private String description;

    @Min(1)
    private int quantity;

    @NotNull(message = "brandName must be not null")
    private String brandName;

    @NotNull(message = "brandName must be not null")
    private String categoryName;

    @NotEmpty(message = "sizeName must be not empty")
    private List<String> sizeName;
}
