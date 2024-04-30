package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.RoleEntity;
import com.skyline.SalesManager.enums.CodeRole;
import com.skyline.SalesManager.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByCodeRole(CodeRole code);
}
