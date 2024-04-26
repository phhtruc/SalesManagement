package com.skyline.SalesManager.service.impl;

import com.skyline.SalesManager.dto.FileDTO;
import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.entity.*;
import com.skyline.SalesManager.repository.*;
import com.skyline.SalesManager.service.ImageService;
import com.skyline.SalesManager.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final SizeRepository sizeRepository;
    private final ImageService imageService;

    @Override
    public void addProduct(ProductDTO p, List<MultipartFile> multipartFile) {
        BrandEntity brand = brandRepository.findByBrandName(p.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        CategoryEntity category = categoryRepository.findByCateName(p.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Cate not found"));
        List<SizeEntity> sizeEntitySet = p.getSizeName().stream()
                .map(sizeName -> sizeRepository.findBySizeName(sizeName)
                        .orElseThrow(() -> new RuntimeException("Size not found")))
                .collect(Collectors.toList());

        var product = ProductEntity.builder()
                .productName(p.getProductName())
                .price(p.getPrice())
                .description(p.getDescription())
                .quantity(p.getQuantity())
                .brandEntity(brand)
                .categoryEntity(category)
                .sizes(sizeEntitySet)
                .build();

        List<ProductImageEntity> productImages = multipartFile.stream()
                .map(multipartFile1 -> {
                    String imageUrl = imageService.upload(multipartFile1);
                    return ProductImageEntity.builder()
                            .image(imageUrl)
                            .productEntity(product)
                            .build();
                })
                .collect(Collectors.toList());
        productRepository.save(product);
        productImageRepository.saveAll(productImages);
    }

    @Override
    public void updateProduct(long id, ProductDTO productDTO) {
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
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
