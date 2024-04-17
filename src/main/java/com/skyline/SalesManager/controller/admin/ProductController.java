package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.repository.ProductRepository;
import com.skyline.SalesManager.service.ProductService;
import com.skyline.SalesManager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAllProduct());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody ProductDTO product){
        productService.addProduct(product);
        Map<String, Object> responseData = Map.of(
                "name", product.getProductName(),
                "price", product.getPrice(),
                "description", product.getDescription(),
                "quantity", product.getQuantity(),
                "brandName", product.getBrandName(),
                "categoryName", product.getCategoryName()
        );

        return responseUtil.createSuccessResponse(responseData, "/api/v1/products/");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(200);
    }
}
