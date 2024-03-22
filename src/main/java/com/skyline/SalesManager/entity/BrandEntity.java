package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Brand")
public class BrandEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_brand;

    @Column(name="brand_name")
    private String brand_name;
}
