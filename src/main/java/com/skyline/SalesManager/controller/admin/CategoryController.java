package com.skyline.SalesManager.controller.admin;

import com.skyline.SalesManager.dto.request.CategoryRequest;
import com.skyline.SalesManager.dto.response.ResponseData;
import com.skyline.SalesManager.repository.CategoryRepository;
import com.skyline.SalesManager.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseData<?> getAllCategory(){
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Get All Category Success", categoryRepository.findAll());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Get All Category Fail");
        }
    }

    @PostMapping
    public ResponseData<?> addCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        try {
            return new ResponseData<>(HttpStatus.CREATED.value(), "Add Category Success", categoryService.addCategory(categoryRequest));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Add Category Fail");
        }
    }

    @PutMapping("/{id}")
    public ResponseData<?> updateCategory(@PathVariable Long id,
                                        @RequestBody @Valid CategoryRequest categoryRequest){
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "Update Category Success", categoryService.updateCategory(id, categoryRequest));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Update Category Fail");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseData<?> deleteCategory(@PathVariable Long id){
        try {
            categoryRepository.deleteById(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete Category Success");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), "Delete Category Fail");
        }
    }
}
