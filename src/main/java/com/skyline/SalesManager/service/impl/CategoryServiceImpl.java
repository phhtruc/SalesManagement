package com.skyline.SalesManager.service.impl;

import com.skyline.SalesManager.dto.request.CategoryRequest;
import com.skyline.SalesManager.entity.CategoryEntity;
import com.skyline.SalesManager.repository.CategoryRepository;
import com.skyline.SalesManager.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> addCategory(CategoryRequest categoryRequest) {
        var category = CategoryEntity.builder()
                .cateName(categoryRequest.getCategoryName())
                .build();
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity updateCategory(Long id, CategoryRequest categoryRequest) {
        CategoryEntity category = categoryRepository.findById(id)
                .map(categoryEntity -> {
                    categoryEntity.setCateName(categoryRequest.getCategoryName());
                    return categoryRepository.save(categoryEntity);
                }).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.flush();
        return category;
    }
}
