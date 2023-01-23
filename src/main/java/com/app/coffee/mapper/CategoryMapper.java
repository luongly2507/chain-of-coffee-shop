package com.app.coffee.mapper;

import com.app.coffee.entity.Category;
import com.app.coffee.payload.response.CategoryResponse;

public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);
    
}
