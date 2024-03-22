package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@Entity
@Table(name = "OrderDetails")
public class OrderDetailsEntity extends BaseEntity{

    @EmbeddedId
    private OrderDetailsId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @MapsId("idProduct")
    @JoinColumn(name = "id_product", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    @MapsId("idOrder")
    @JoinColumn(name = "id_order", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderEntity orderEntity;

    @Column(name = "quantity")
    @Positive
    private Integer quantity;

    @Column(name = "size")
    private String size;

    @Column(name = "total")
    @Min(value = 0)
    private Double total;

}

@Embeddable
@Data
class OrderDetailsId implements Serializable {

    @Column(name = "id_order")
    private long idOrder;

    @Column(name = "id_product")
    private long idProduct;

}

