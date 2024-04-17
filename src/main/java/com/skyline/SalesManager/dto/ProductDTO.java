package com.skyline.SalesManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private long idProduct;
    private String productName;
    private Double price;
    private String description;
    private int quantity;
    private String brandName;
    private String categoryName;

    public ProductDTO(Object[] objects) {
        this.idProduct = (Long) objects[0];
        this.productName = (String) objects[1];
        this.price = (Double) objects[2];
        this.description = (String) objects[3];
        this.quantity = (Integer) objects[4];
        this.brandName = (String) objects[5];
        this.categoryName = (String) objects[6];
    }
}
