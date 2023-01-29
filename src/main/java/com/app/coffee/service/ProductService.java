package com.app.coffee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.app.coffee.payload.request.CreateProductRequest;
import com.app.coffee.payload.request.UpdateProductRequest;
import com.app.coffee.payload.response.ProductResponse;

import jakarta.validation.Valid;

public interface ProductService {
    public List<ProductResponse> getAllProducts();
    public Page<ProductResponse> getAllProducts(Pageable pageable);
    public ProductResponse getProductById(UUID productId);
    public void deleteProduct(UUID productId);
    public ProductResponse createProduct(MultipartFile image, @Valid CreateProductRequest createProductRequest);
    public void updateProduct(UUID productId, @Valid UpdateProductRequest updateProductRequest);
    public Page<ProductResponse> getAllProductsByName(String search, Pageable pageable);
}
