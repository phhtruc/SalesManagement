package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.repository.ProductRepository;
import com.skyline.SalesManager.service.ProductService;
import com.skyline.SalesManager.service.impl.ProductServiceImpl;
import com.skyline.SalesManager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final ResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        return ResponseEntity.ok(productRepository.findAllProduct());
    }


    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductDTO> getOneProduct(@PathVariable long idProduct){
        return ResponseEntity.ok(productRepository.findOneProduct(idProduct));
    }

    /*@PostMapping
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
    }*/

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestParam("file") List<MultipartFile> multipartFile,
                                        @ModelAttribute ProductDTO productDTO){
        productService.addProduct(productDTO, multipartFile);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Long idProduct, @RequestBody ProductDTO productDTO){
        productService.updateProduct(idProduct, productDTO);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/{idProduct}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteProduct(@PathVariable Long idProduct){
        productRepository.deleteById(idProduct);
        return ResponseEntity.ok(200);
    }
}
