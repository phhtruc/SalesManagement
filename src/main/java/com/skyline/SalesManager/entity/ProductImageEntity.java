package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "ProductImage")
public class ProductImageEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_productImage;

    @Column(name = "[image]", columnDefinition = "TEXT")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_product", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;
}
