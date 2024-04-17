package com.skyline.SalesManager.service;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.entity.BrandEntity;
import com.skyline.SalesManager.entity.CategoryEntity;
import com.skyline.SalesManager.entity.ProductEntity;
import com.skyline.SalesManager.repository.BrandRepository;
import com.skyline.SalesManager.repository.CategoryRepository;
import com.skyline.SalesManager.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;

    public void addProduct(ProductDTO p){

        Optional<BrandEntity> brand = Optional.of(brandRepository.findByBrandName(p.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found")));
        Optional<CategoryEntity> category = Optional.of(categoryRepository.findByCateName(p.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Cate not found")));

        var product = ProductEntity.builder()
                        .productName(p.getProductName())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .quantity(p.getQuantity())
                        .brandEntity(brand.get())
                        .categoryEntity(category.get())
                        .build();
        productRepository.save(product);
    }

    public void updateProduct(long id, ProductDTO productDTO){

        Optional<BrandEntity> brand = Optional.of(brandRepository.findByBrandName(productDTO.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found")));
        Optional<CategoryEntity> category = Optional.of(categoryRepository.findByCateName(productDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Cate not found")));

        productRepository.findById(id).map(p -> {
            p.setProductName(productDTO.getProductName());
            p.setPrice(productDTO.getPrice());
            p.setDescription(productDTO.getDescription());
            p.setQuantity(productDTO.getQuantity());
            p.setBrandEntity(brand.get());
            p.setCategoryEntity(category.get());
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found with id "+ productDTO.getIdProduct()));
    }
}
