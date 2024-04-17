package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass // định nghĩa 1 lớp entity cha
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name="createdDate", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name="modifiedDate", updatable = false)
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Column(name="createdBy")
    @CreatedBy
    private String createdBy;

    @Column(name="modifiedBy")
    @LastModifiedBy
    private String modifiedBy;

}
