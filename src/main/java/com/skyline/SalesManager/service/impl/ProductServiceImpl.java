package com.skyline.SalesManager.service.impl;

import com.skyline.SalesManager.dto.request.ProductRequestDTO;
import com.skyline.SalesManager.dto.response.PageResponse;
import com.skyline.SalesManager.dto.response.ProductResponseDTO;
import com.skyline.SalesManager.entity.*;
import com.skyline.SalesManager.repository.*;
import com.skyline.SalesManager.service.ImageService;
import com.skyline.SalesManager.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final SizeRepository sizeRepository;
    private final ImageService imageService;

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO p, List<MultipartFile> multipartFile) {
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

        return ProductResponseDTO.builder()
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
    public void updateProduct(long id, ProductRequestDTO productDTO, List<MultipartFile> multipartFile) {
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
            p.getSizes().clear();
            p.setSizes(sizeEntitySet);
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Product not found"));

        // Update file
        productImageRepository.deleteByIdProductEntity(id);
        List<ProductImageEntity> productImageEntity = multipartFile.stream()
                .map(multipartFile1 -> {
                    String imageUrl = imageService.upload(multipartFile1);
                    return ProductImageEntity.builder()
                            .image(imageUrl)
                            .productEntity(product)
                            .build();
                })
                .collect(Collectors.toList());
        productImageRepository.saveAll(productImageEntity);
    }

    @Override
    public ProductResponseDTO findProductById(long idProduct) {
        Optional<List<String>> listSizeName = sizeRepository.findSizeNameByIdProduct(idProduct);
        Optional<List<String>> listFileName = productImageRepository.findImageNameByIdProduct(idProduct);
        Optional<ProductResponseDTO> responseDTO = productRepository.findProductById(idProduct).map(productResponseDTO -> {
            productResponseDTO.setSizeName(listSizeName.orElseThrow());
            productResponseDTO.setFileName(listFileName.orElseThrow());
            return productResponseDTO;
        });
        return responseDTO.orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public PageResponse<?> findAllProducts(int pageNo, int pageSize, String seacrh) {
        int page = 0;
        if(pageNo > 0){
            page = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("idProduct").ascending());
        //Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC), sortBy);

        if(StringUtils.hasText(seacrh)){
            Page<ProductResponseDTO> productResponseDTOS = productRepository.findAllProduct(pageable, seacrh);

            List<ProductResponseDTO> pro = productResponseDTOS.stream()
                    .peek(p -> {
                        p.setSizeName(sizeRepository.findSizeNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("Size not found")));
                        p.setFileName(productImageRepository.findImageNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("File not found")));
                    })
                    .collect(Collectors.toList());

            return PageResponse.builder()
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .totalPage(productResponseDTOS.getTotalPages())
                    .items(pro)
                    .build();
        }
        else {
            Page<ProductResponseDTO> productResponseDTOS = productRepository.findAllProduct(pageable);

            List<ProductResponseDTO> pro = productResponseDTOS.stream()
                    .peek(p -> {
                        p.setSizeName(sizeRepository.findSizeNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("Size not found")));
                        p.setFileName(productImageRepository.findImageNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("File not found")));
                    })
                    .collect(Collectors.toList());

            return PageResponse.builder()
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .totalPage(productResponseDTOS.getTotalPages())
                    .items(pro)
                    .build();
        }
    }

    @Override
    public PageResponse<?> getProductsByCategory(String category, int pageNo, int pageSize) {
        int page = 0;
        if(pageNo > 0){
            page = pageNo - 1;
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("idProduct").ascending());
        Page<ProductResponseDTO> productResponseDTOS = productRepository.findProductsByCategoryName(pageable, category);

        List<ProductResponseDTO> pro = productResponseDTOS.stream()
                .peek(p -> {
                    p.setSizeName(sizeRepository.findSizeNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("Size not found")));
                    p.setFileName(productImageRepository.findImageNameByIdProduct(p.getIdProduct()).orElseThrow(() -> new RuntimeException("File not found")));
                })
                .collect(Collectors.toList());

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPage(productResponseDTOS.getTotalPages())
                .items(pro)
                .build();
    }

}
