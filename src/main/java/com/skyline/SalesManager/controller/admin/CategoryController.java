package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.entity.CategoryEntity;
import com.skyline.SalesManager.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categorys")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    @GetMapping
    public Optional<CategoryEntity> getAllCategory(@RequestParam(required = false) String name){
        Optional<CategoryEntity> cates = categoryRepository.findByCateName(name);
        return  cates;
    }
}
