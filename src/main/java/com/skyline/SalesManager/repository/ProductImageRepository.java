package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {
}
