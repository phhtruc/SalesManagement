package com.skyline.SalesManager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skyline.SalesManager.enums.CodeRole;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "role")
public class RoleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRole;

    @Enumerated(EnumType.STRING)
    private CodeRole codeRole;

    @Column(name = "role_name")
    private String roleName;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    List<UserEntity> userEntities = new ArrayList<>();

}

