package com.skyline.SalesManager.dto.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class ProductResponseDTO {

    private Long idProduct;

    private String productName;

    private Double price;

    private String description;

    private Integer quantity;

    private String brandName;

    private String categoryName;

    private List<String> sizeName;

    private List<String> fileName;

    public ProductResponseDTO(Long idProduct, String productName, Double price, String description,
                              Integer quantity, String brandName, String categoryName) {
        this.idProduct = idProduct;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.brandName = brandName;
        this.categoryName = categoryName;
    }
}
