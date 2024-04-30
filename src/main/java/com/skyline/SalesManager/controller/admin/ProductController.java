package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.repository.ProductRepository;
import com.skyline.SalesManager.response.ResponseData;
import com.skyline.SalesManager.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    @GetMapping
    public ResponseData<?> getAllProducts(){
        List<ProductDTO> productDTO = productService.findAllProducts();
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get All Product Success", productDTO);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Get All Product Failed");
        }

    }

    @GetMapping("/{idProduct}")
    public ResponseData<?> getOneProduct(@PathVariable long idProduct){
        ProductDTO productDTO = productService.findProductById(idProduct);
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get Product success", productDTO);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Get Product Failed");
        }
    }

    @PostMapping
    public ResponseData<?> addProduct(@RequestParam("file") List<MultipartFile> multipartFile,
                                   @Valid @ModelAttribute ProductDTO productDTO){
        ProductDTO dto = productService.addProduct(productDTO, multipartFile);
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(), "Add product success", dto);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Add Product Failed");
        }
    }

    @PatchMapping("/{idProduct}")
    public ResponseEntity<?> updateProduct(@PathVariable Long idProduct, @RequestBody ProductDTO productDTO){
        productService.updateProduct(idProduct, productDTO);
        return ResponseEntity.ok(200);
    }

    @DeleteMapping("/{idProduct}")
    public ResponseData<?> deleteProduct(@PathVariable Long idProduct){
        try {
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete product success");
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Delete Product Failed");
        }
    }
}
