package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.request.ProductRequestDTO;
import com.skyline.SalesManager.dto.response.ProductResponseDTO;
import com.skyline.SalesManager.dto.response.ResponseError;
import com.skyline.SalesManager.repository.ProductRepository;
import com.skyline.SalesManager.dto.response.ResponseData;
import com.skyline.SalesManager.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
@Tag(name ="Product Controller")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Operation(summary = "Get All Product", description = "API get all product")
    @GetMapping
    public ResponseData<?> getAllProducts(@RequestParam(required = false, defaultValue = "1") int pageNo,
                                          @Min(10) @RequestParam(required = false, defaultValue = "10") int pageSize){
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get All Product Success", productService.findAllProducts(pageNo,pageSize));
        }catch (Exception e){
            log.error("errorMessage{}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Get All Product Failed");
        }
    }

    @GetMapping("/{idProduct}")
    public ResponseData<?> getOneProduct(@PathVariable long idProduct){
        try {
            ProductResponseDTO productDTO = productService.findProductById(idProduct);
            return new ResponseData<>(HttpStatus.OK.value(), "Get Product success", productDTO);
        }catch (Exception e){
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @PostMapping
    public ResponseData<?> addProduct(@RequestParam(value = "file", required = false) List<MultipartFile> multipartFile,
                                   @Valid @ModelAttribute ProductRequestDTO productDTO){
        try {
            ProductResponseDTO dto = productService.addProduct(productDTO, multipartFile);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Add product success", dto);
        }catch (Exception e){
            log.error("errorMessage{}",e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add product failed");
        }
    }

    @PutMapping("/{idProduct}")
    public ResponseData<?> updateProduct(@PathVariable Long idProduct,
                                           @Valid @ModelAttribute ProductRequestDTO productDTO,
                                           @RequestParam(value = "file", required = false) List<MultipartFile> multipartFile){
        try {
            productService.updateProduct(idProduct, productDTO, multipartFile);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Update product success");
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }
    }

    @DeleteMapping("/{idProduct}")
    public ResponseData<?> deleteProduct(@PathVariable Long idProduct){
        try {
            productRepository.deleteById(idProduct);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete product success");
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Delete Product Failed");
        }
    }
}
