package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "voucher")
public class VoucherEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idVoucher;

    @Column(name = "voucher_code", unique = true)
    private String VoucherCode;

    @Column(name = "discount_amount")
    @Positive
    private double discountAmount;

    @Column(name = "percentage")
    @Positive
    private int percentage;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "usage_limit")
    private int usageLimit;

    @Column(name = "condition_price")
    @Min(value = 0)
    private int conditionPrice;

    @ManyToMany(mappedBy = "vouchers")
    List<UserEntity> userEntityList = new ArrayList<>();

}
