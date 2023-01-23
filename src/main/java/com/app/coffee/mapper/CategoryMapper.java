package com.app.coffee.mapper;

import com.app.coffee.entity.Category;
import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.payload.response.CategoryResponse;

public interface CategoryMapper {

    public CategoryResponse toCategoryResponse(Category category);
    public Category toCategory(CreateCategoryRequest createCategoryRequest);
    public void updateCategory (UpdateCategoryRequest updateCategory, Category category);
}
