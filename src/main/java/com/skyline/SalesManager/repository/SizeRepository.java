package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {
    Optional<SizeEntity> findBySizeName(String sizeName);
}
