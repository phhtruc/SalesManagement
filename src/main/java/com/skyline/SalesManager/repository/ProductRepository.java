package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.dto.ProductDTO;
import com.skyline.SalesManager.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("""
        select p.idProduct, p.productName, p.price, p.description, p.quantity, b.brandName, c.cateName
        from ProductEntity p join p.brandEntity b join p.categoryEntity c
    """)
    List<ProductDTO> findAllProducts();

    @Query("""
    select new com.skyline.SalesManager.dto.ProductDTO(p.idProduct, p.productName, p.price, p.description, p.quantity, b.brandName, c.cateName)
    from ProductEntity p join p.brandEntity b join p.categoryEntity c
""")
    List<ProductDTO> findAllProduct();

/*    @Query("SELECT new com.skyline.SalesManager.dto.ProductDTO(p.id_product, p.product_name, p.price, p.Description, p.quantity, b.brand_name, c.cate_name, s.sizeName) " +
            "FROM ProductEntity p " +
            "LEFT JOIN p.brandEntity b " +
            "LEFT JOIN p.categoryEntity c " +
            "LEFT JOIN p.sizes s")
    List<ProductDTO> findAllProducts();*/
}
