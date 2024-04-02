package com.skyline.SalesManager.repository;

import com.skyline.SalesManager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email); // Sử dụng Optional để kiểm tra và tránh lỗi NullPointerException
}
