package com.skyline.SalesManager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductDTO{

    private String productName;
    private Double price;
    private String description;
    private int quantity;
    private String brandName;
    private String categoryName;
    private List<String> sizeName;
}
