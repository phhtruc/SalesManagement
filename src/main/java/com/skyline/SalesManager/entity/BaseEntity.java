package com.skyline.SalesManager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@MappedSuperclass // định nghĩa 1 lớp entity cha
public abstract class BaseEntity {

    @Column(name="createdDate")
    @CreatedDate // tự tạo createdDate nên không cần hàm set
    private Date createdDate;

    @Column(name="modifiedDate")
    @LastModifiedDate
    private Date modifiedDate;

    @Column(name="createdBy")
    @CreatedBy
    private String createdBy;

    @Column(name="modifiedBy")
    @LastModifiedBy
    private String modifiedBy;

}
