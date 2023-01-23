package com.app.coffee.mapper.impl;

import org.springframework.stereotype.Component;

import com.app.coffee.entity.Category;
import com.app.coffee.mapper.CategoryMapper;
import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.payload.response.CategoryResponse;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        if (category == null){
            return null;
        }
        return CategoryResponse.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .build();
    }

    @Override
    public Category toCategory(CreateCategoryRequest createCategoryRequest) {
        if(createCategoryRequest == null) {
            return null;
        }
        return Category.builder()
            .name(createCategoryRequest.getName())            
            .description(createCategoryRequest.getDescription())
            .build();
    }

    @Override
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest, Category category) {
        if(updateCategoryRequest == null){
            return;
        }
        category.setName(updateCategoryRequest.getName());
        category.setDescription(updateCategoryRequest.getDescription());
    }
    
}
