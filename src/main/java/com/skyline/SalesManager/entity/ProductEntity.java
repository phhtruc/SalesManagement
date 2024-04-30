package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "Product")
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProduct;

    @Column(name="productName")
    private String productName;

    @Column(name="price")
    @Positive // đảm bảo là số dương
    private Double price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity")
    @Min(1)
    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE) // optional entity bat buoc, phai co .
    @JoinColumn(name = "idBrand", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private BrandEntity brandEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE) // optional entity bat buoc, phai co .
    @JoinColumn(name = "idCate", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CategoryEntity categoryEntity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Product_size", joinColumns = @JoinColumn(name = "idProduct"),
            inverseJoinColumns = @JoinColumn(name = "idSize"))
    List<SizeEntity> sizes = new ArrayList<>();
}
