package com.app.coffee.service;

import java.util.List;

import com.app.coffee.payload.response.CategoryResponse;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();
    
}
