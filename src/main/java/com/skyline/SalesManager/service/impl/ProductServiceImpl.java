package com.skyline.SalesManager.service.impl;

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
import java.util.stream.Stream;

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
    public ProductDTO addProduct(ProductDTO p, List<MultipartFile> multipartFile) {
        BrandEntity brand = brandRepository.findByBrandName(p.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        CategoryEntity category = categoryRepository.findByCateName(p.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Cate not found"));
        List<SizeEntity> sizeEntitySet = p.getSizeName().stream()
                .map(sizeName -> sizeRepository.findBySizeName(sizeName)
                        .orElseThrow(() -> new RuntimeException("Size not found")))
                .flatMap(List::stream) // Làm phẳng danh sách các SizeEntity thành một Stream duy nhất
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

        return ProductDTO.builder()
                .idProduct(product.getIdProduct())
                .productName(p.getProductName())
                .price(p.getPrice())
                .description(p.getDescription())
                .quantity(p.getQuantity())
                .brandName(p.getBrandName())
                .categoryName(p.getCategoryName())
                .sizeName(p.getSizeName())
                .fileName(productImageRepository.findImageNameByIdProduct(product.getIdProduct())
                        .orElseThrow())
                .build();
    }

    @Override
    public void updateProduct(long id, ProductDTO productDTO, List<MultipartFile> multipartFile) {
        Optional<BrandEntity> brand = Optional.of(brandRepository.findByBrandName(productDTO.getBrandName())
                .orElseThrow(() -> new RuntimeException("Brand not found")));
        Optional<CategoryEntity> category = Optional.of(categoryRepository.findByCateName(productDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Cate not found")));
        List<SizeEntity> sizeEntitySet = productDTO.getSizeName().stream()
                .map(sizeName -> sizeRepository.findBySizeName(sizeName)
                        .orElseThrow(() -> new RuntimeException("Size not found")))
                .flatMap(List::stream) // Làm phẳng danh sách các SizeEntity thành một Stream duy nhất
                .collect(Collectors.toList());

        ProductEntity product = productRepository.findById(id).map(p -> {
            p.setProductName(productDTO.getProductName());
            p.setPrice(productDTO.getPrice());
            p.setDescription(productDTO.getDescription());
            p.setQuantity(productDTO.getQuantity());
            p.setBrandEntity(brand.get());
            p.setCategoryEntity(category.get());
            p.setSizes(sizeEntitySet);
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));

        //Update fileName
        for (MultipartFile file : multipartFile) {
            String imageUrl = imageService.upload(file);
            List<String> imageNames = productImageRepository.findImageNameByIdProduct(product.getIdProduct())
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            for (String imageName : imageNames) {
                List<ProductImageEntity> imageEntities = productImageRepository.findByImageName(imageName);
                for (ProductImageEntity imageEntity : imageEntities) {
                    imageEntity.setImage(imageUrl);
                    productImageRepository.save(imageEntity);
                }
            }
        }
        //Update sizeName


    }

    @Override
    public ProductDTO findProductById(long idProduct) {
        Optional<List<String>> listSizeName = sizeRepository.findSizeNameByIdProduct(idProduct);
        Optional<List<String>> listFileName = productImageRepository.findImageNameByIdProduct(idProduct);
        ProductDTO productDTO = productRepository.findProductById(idProduct);
        productDTO.setSizeName(listSizeName.orElseThrow(() -> new RuntimeException("Size not found")));
        productDTO.setFileName(listFileName.orElseThrow(() -> new RuntimeException("File not found")));
        return productDTO;
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        return productRepository.findAllProduct().stream()
                .peek(p -> {
                    p.setSizeName(sizeRepository.findSizeNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("Size not found")));
                    p.setFileName(productImageRepository.findImageNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("File not found")));
                })
                .collect(Collectors.toList());
    }

}
