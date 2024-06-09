package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.repository.ProductRepository;
import com.skyline.SalesManager.response.ResponseData;
import com.skyline.SalesManager.response.ResponseError;
import com.skyline.SalesManager.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductService productService;

    @GetMapping
    public ResponseData<?> getAllProducts(){
        try {
            List<ProductDTO> productDTO = productService.findAllProducts();
            return new ResponseData<>(HttpStatus.OK.value(), "Get All Product Success", productDTO);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Get All Product Failed");
        }

    }

    @GetMapping("/{idProduct}")
    public ResponseData<?> getOneProduct(@PathVariable long idProduct){
        try {
            ProductDTO productDTO = productService.findProductById(idProduct);
            return new ResponseData<>(HttpStatus.OK.value(), "Get Product success", productDTO);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Get Product Failed");
        }
    }

    @PostMapping
    public ResponseData<?> addProduct(@RequestParam(value = "file", required = false) List<MultipartFile> multipartFile,
                                   @Valid @ModelAttribute ProductDTO productDTO){
        try {
            ProductDTO dto = productService.addProduct(productDTO, multipartFile);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Add product success", dto);
        }catch (Exception e){
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Add Product Failed");
        }
    }

    @PutMapping("/{idProduct}")
    public ResponseData<?> updateProduct(@PathVariable Long idProduct,
                                           @Valid @ModelAttribute ProductDTO productDTO,
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
