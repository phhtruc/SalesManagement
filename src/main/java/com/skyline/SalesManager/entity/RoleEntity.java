package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "[Role]")
public class RoleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_role;

    @Column(name = "code")
    private String code;

    @Column(name = "role_name")
    private String role_name;

    @ManyToMany(mappedBy = "roles")
    List<UserEntity> userEntities = new ArrayList<>();

}

