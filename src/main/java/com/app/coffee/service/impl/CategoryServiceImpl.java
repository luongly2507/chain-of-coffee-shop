package com.app.coffee.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.coffee.mapper.CategoryMapper;
import com.app.coffee.payload.response.CategoryResponse;
import com.app.coffee.repository.CategoryRepository;
import com.app.coffee.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(
            category -> categoryMapper.toCategoryResponse(category)
        ).toList();
    }
    
}
