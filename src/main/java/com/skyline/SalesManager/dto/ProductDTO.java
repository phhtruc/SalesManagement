package com.skyline.SalesManager.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO implements Serializable {

    private long idProduct;

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

    private List<String> fileName;

    public ProductDTO(long idProduct, String productName, @Positive Double price, String description,
                      @Min(1) int quantity, String brandName, String categoryName) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.brandName = brandName;
        this.categoryName = categoryName;
    }

}
