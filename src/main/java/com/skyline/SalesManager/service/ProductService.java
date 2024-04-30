package com.skyline.SalesManager.service;

import com.skyline.SalesManager.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    public ProductDTO addProduct(ProductDTO p, List<MultipartFile> multipartFile);

    public void updateProduct(long id, ProductDTO productDTO);

    public ProductDTO findProductById(long idProduct);

    public List<ProductDTO> findAllProducts();
}
