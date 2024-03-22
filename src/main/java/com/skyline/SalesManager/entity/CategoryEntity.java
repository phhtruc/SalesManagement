package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Category")
public class CategoryEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cate")
    private long id_cate;

    @Column(name="cate_name")
    private String cate_name;
}
