package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

    @Query("""
        select p.image
        from ProductImageEntity p join p.productEntity product
        where product.idProduct = :idProduct
        """)
    Optional<List<String>> findImageNameByIdProduct(@Param("idProduct") long idProduct);

    @Query("SELECT p FROM ProductImageEntity p WHERE p.image = :imageName")
    List<ProductImageEntity> findByImageName(String imageName);


}
