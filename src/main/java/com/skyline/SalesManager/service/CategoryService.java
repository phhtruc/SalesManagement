package com.skyline.SalesManager.service;

import com.skyline.SalesManager.dto.request.CategoryRequest;
import com.skyline.SalesManager.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<CategoryEntity> addCategory(CategoryRequest name);

    CategoryEntity updateCategory(Long id, CategoryRequest categoryRequest);
}
