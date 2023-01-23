package com.app.coffee.mapper.impl;

import org.springframework.stereotype.Component;

import com.app.coffee.entity.Category;
import com.app.coffee.mapper.CategoryMapper;
import com.app.coffee.payload.response.CategoryResponse;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
            .name(category.getName())
            .description(category.getDescription())
            .build();
    }
    
}
