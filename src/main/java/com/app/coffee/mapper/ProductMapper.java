package com.app.coffee.mapper;

import com.app.coffee.entity.Product;
import com.app.coffee.payload.request.CreateProductRequest;
import com.app.coffee.payload.request.UpdateProductRequest;
import com.app.coffee.payload.response.ProductResponse;

public interface ProductMapper {
    public ProductResponse toProductResponse(Product product);
    public Product toProduct(CreateProductRequest createProductRequest);
    public void updateProduct (UpdateProductRequest updateProduct, Product product);
}
