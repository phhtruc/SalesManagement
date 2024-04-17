package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Brand")
public class BrandEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idBrand;

    @Column(name="brandName")
    private String brandName;
}
