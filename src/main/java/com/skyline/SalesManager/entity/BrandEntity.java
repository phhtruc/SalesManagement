package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="brand")
public class BrandEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBrand;

    @Column(name="brand_name")
    private String brandName;
}
