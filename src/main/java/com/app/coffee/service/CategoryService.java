package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.payload.response.CategoryResponse;

import jakarta.validation.Valid;

public interface CategoryService {

    public List<CategoryResponse> getAllCategories();
    public Page<CategoryResponse> getAllCategories(String key,Pageable pageable);
    public CategoryResponse getCategoryById(UUID categoryId);
    public void deleteCategory(UUID categoryId);
    public CategoryResponse createCategory(@Valid CreateCategoryRequest createCategoryRequest);
    public void updateCategory(UUID categoryId, @Valid UpdateCategoryRequest updateCategoryRequest);
    public Page<CategoryResponse> getAllCategoriesByName(String search, Pageable pageable);

}
