package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
            SELECT new com.skyline.SalesManager.dto.ProductDTO(p.idProduct, p.productName, p.price, p.description, p.quantity, b.brandName, c.cateName)
            FROM ProductEntity p
            JOIN p.brandEntity b
            JOIN p.categoryEntity c
            """)
    List<ProductDTO> findAllProduct();


    @Query("""
    select new com.skyline.SalesManager.dto.ProductDTO(p.idProduct, p.productName, p.price, p.description, p.quantity, b.brandName, c.cateName)
    from ProductEntity p join p.brandEntity b join p.categoryEntity c
    where p.idProduct = :idProduct
    """)
    ProductDTO findProductById(Long idProduct);

}
