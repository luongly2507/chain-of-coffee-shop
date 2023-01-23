package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.entity.Category;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.CategoryMapper;
import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.payload.response.CategoryResponse;
import com.app.coffee.repository.CategoryRepository;
import com.app.coffee.service.CategoryService;

import jakarta.validation.Valid;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    // Get Mapping - Get All
    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(
            category -> categoryMapper.toCategoryResponse(category)
        ).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
        .map(category->categoryMapper.toCategoryResponse(category));
    }

    // Get Mapping - Get By Id
    @Override
    public CategoryResponse getCategoryById(UUID categoryId) {
        return categoryMapper.toCategoryResponse(
            categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category Not Found"))); // Check category is present
    }

    // Post Mapping - Create Category
    @Override
    public CategoryResponse createCategory(@Valid CreateCategoryRequest createCategoryRequest) {
        Category category = categoryMapper.toCategory(createCategoryRequest);
        // Check category is not null
        if (category == null) {
            throw new BadRequestException("Bad Request");
        }
         // Check name is unique
        if (categoryRepository.existsByName(createCategoryRequest.getName())) {
            throw new ConflictException("Already Name Exists.");
        }
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));       
    }

    // Put Mapping - Update Category
    @Override
    public void updateCategory(UUID categoryId, @Valid UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Category Not Found."));
          // Check name is unique
          if (!updateCategoryRequest.getName().equals(category.getName())) {
            if (categoryRepository.existsByName(updateCategoryRequest.getName())) {
                throw new ConflictException("Already Name Exists.");
            }
        }
        categoryMapper.updateCategory(updateCategoryRequest, category);
        categoryRepository.save(category);
    }

    // Delete Mapping -
    @Override
    public void deleteCategory(UUID categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new ResourceNotFoundException("Category Not Found");  // Check category is present
        }
    }


}
