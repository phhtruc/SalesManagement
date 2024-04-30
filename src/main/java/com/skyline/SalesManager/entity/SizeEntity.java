package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Size")
public class SizeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCate")
    private CategoryEntity categoryEntity;

    @Column(name = "sizeName")
    private String sizeName;

    @ManyToMany(mappedBy = "sizes")
    List<ProductEntity> productEntities = new ArrayList<>();
}
