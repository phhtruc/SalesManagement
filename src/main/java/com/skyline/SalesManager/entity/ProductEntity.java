package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Product")
public class ProductEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_product;

    @Column(name="product_name")
    private String product_name;

    @Column(name="price")
    @Positive // đảm bảo là số dương
    private Double price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String Description;

    @Column(name = "quantity")
    @Positive
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE) // optional entity bat buoc, phai co .
    @JoinColumn(name = "id_brand", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BrandEntity brandEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE) // optional entity bat buoc, phai co .
    @JoinColumn(name = "id_cate", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoryEntity categoryEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Product_size", joinColumns = @JoinColumn(name = "id_product"),
            inverseJoinColumns = @JoinColumn(name = "id_size"))
    List<SizeEntity> sizes = new ArrayList<>();


}
