package com.app.coffee.mapper.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.coffee.entity.Category;
import com.app.coffee.entity.Product;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.CategoryMapper;
import com.app.coffee.mapper.ProductMapper;
import com.app.coffee.payload.request.CreateProductRequest;
import com.app.coffee.payload.request.UpdateProductRequest;
import com.app.coffee.payload.response.ProductResponse;
import com.app.coffee.repository.CategoryRepository;

@Component
public class ProductMapperImpl implements ProductMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductResponse toProductResponse(Product product) {
        if (product == null) {
            return null;
        }
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .image(product.getImage())
                .category(categoryMapper.toCategoryResponse(product.getCategory()))
                .description(product.getDescription())
                .createdAt(product.getCreatedAt())
                .createdBy(product.getCreatedBy())
                .lastModifiedAt(product.getLastModifiedAt())
                .lastModifiedBy(product.getLastModifiedBy())
                .build();
    }

    @Override
    public Product toProduct(CreateProductRequest createProductRequest) {
        if (createProductRequest == null) {
            return null;
        }
        Category category = categoryRepository.findById(UUID.fromString(createProductRequest.getCategoryID()))
                .orElseThrow(
                        () -> new ResourceNotFoundException("Category Not Found!"));

        return Product.builder()
                .name(createProductRequest.getName())
                .price(createProductRequest.getPrice())
                .category(category)
                .description(createProductRequest.getDescription())
                .build();
    }

    @Override
    public void updateProduct(UpdateProductRequest updateProductRequest, Product product) {
        if (updateProductRequest == null) {
            return;
        }
        Category category = categoryRepository.findById(UUID.fromString(updateProductRequest.getCategoryID()))
        .orElseThrow(
                () -> new ResourceNotFoundException("Category Not Found!"));

        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setCategory(category);
        product.setDescription(updateProductRequest.getDescription());
    }

}
