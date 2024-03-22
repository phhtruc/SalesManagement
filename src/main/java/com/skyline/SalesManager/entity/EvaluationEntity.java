package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@Entity
@Table(name = "Evaluation")
public class EvaluationEntity extends BaseEntity{

    @EmbeddedId
    private EvaluationId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @MapsId("idCust")
    @JoinColumn(name = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;

    @Column(name="title")
    private String title;

    @Column(name="thumbnail")
    private String thumbnail;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "imageContent")
    private String imageContent;
}
@Embeddable
@Data
class EvaluationId implements Serializable {

    @Column(name = "id_cust")
    private long idCust;

    @Column(name = "id_product")
    private long idProduct;
}
