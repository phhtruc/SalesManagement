package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.ProductImageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.beans.Transient;
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

    @Modifying
    @Transactional
    @Query("delete from ProductImageEntity p where p.productEntity.idProduct = :idProduct")
    void deleteByIdProductEntity(@Param("idProduct") long idProduct);

}
