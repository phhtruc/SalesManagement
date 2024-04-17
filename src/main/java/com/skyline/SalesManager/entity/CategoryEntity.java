package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class CategoryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCate;

    @Column(name="cateName")
    private String cateName;
}
