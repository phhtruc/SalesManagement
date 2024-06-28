package com.skyline.SalesManager.service;

import com.skyline.SalesManager.dto.request.ProductRequestDTO;
import com.skyline.SalesManager.dto.response.PageResponse;
import com.skyline.SalesManager.dto.response.ProductResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {

    ProductResponseDTO addProduct(ProductRequestDTO p, List<MultipartFile> multipartFile);

    void updateProduct(long id, ProductRequestDTO productDTO, List<MultipartFile> multipartFile);

    ProductResponseDTO findProductById(long idProduct);

    PageResponse<?> findAllProducts(int pageNo, int pageSize);
}
