package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "size")
public class SizeEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cate")
    private CategoryEntity categoryEntity;

    @Column(name = "size_name")
    private String sizeName;

    @ManyToMany(mappedBy = "sizes")
    List<ProductEntity> productEntities = new ArrayList<>();
}
