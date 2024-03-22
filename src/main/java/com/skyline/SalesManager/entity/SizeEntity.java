package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Size")
public class SizeEntity extends BaseEntity{

    @EmbeddedId
    private SizeId sizeId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("id_cate")
    @JoinColumn(name = "id_cate")
    private CategoryEntity categoryEntity;

    @Column(name = "size_name")
    private String sizeName;

    @ManyToMany(mappedBy = "sizes")
    List<ProductEntity> productEntities = new ArrayList<>();
}

@Embeddable
@Data
class SizeId implements Serializable {

    @Column(name = "id_cate")
    private long idCate;
}
