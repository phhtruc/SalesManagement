package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.SizeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SizeRepository extends JpaRepository<SizeEntity, Long> {

    @Query("""
        select s.sizeName
        from ProductEntity p join p.sizes s
        where p.idProduct = :idProduct
    """)
    Optional<List<String>> findSizeNameByIdProduct(long idProduct);

    Optional<List<SizeEntity>> findBySizeName(String name);

}
