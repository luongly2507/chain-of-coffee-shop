package com.app.coffee.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.coffee.entity.Product;
import com.app.coffee.exception.BadRequestException;
import com.app.coffee.exception.ConflictException;
import com.app.coffee.exception.ResourceNotFoundException;
import com.app.coffee.mapper.ProductMapper;
import com.app.coffee.payload.request.CreateProductRequest;
import com.app.coffee.payload.request.UpdateProductRequest;
import com.app.coffee.payload.response.ProductResponse;
import com.app.coffee.repository.ProductRepository;
import com.app.coffee.service.ProductService;
import com.app.coffee.service.StorageService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductMapper productMapper;

    // Get Mapping - Get All
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(
            product -> productMapper.toProductResponse(product)
        ).toList();
    }

    // Get Mapping - Get By Page
    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
        .map(product->productMapper.toProductResponse(product));
    }

    // Get Mapping - Get By Id
    @Override
    public ProductResponse getProductById(UUID productId) {
        return productMapper.toProductResponse(
            productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product Not Found"))); // Check product is present
    }

    // Post Mapping - Create Product
    @Override
    public ProductResponse createProduct(MultipartFile image, @Valid CreateProductRequest createProductRequest) {
        Product product = productMapper.toProduct(createProductRequest);
        // Check product is not null
        if (product == null) {
            throw new BadRequestException("Bad Request");
        }
         // Check name is unique
        if (productRepository.existsByName(createProductRequest.getName())) {
            throw new ConflictException("Already Name Exists.");
        }

        product.setImage(storageService.store(image));
        return productMapper.toProductResponse(productRepository.save(product));       
    }

    // Put Mapping - Update Product
    @Override
    public void updateProduct(UUID productId, @Valid UpdateProductRequest updateProductRequest) {
        Product product = productRepository.findById(productId)
            .orElseThrow(
                ()-> new ResourceNotFoundException("Product Not Found."));
          // Check name is unique
          if (!updateProductRequest.getName().equals(product.getName())) { // compare request name and repository name
            if (productRepository.existsByName(updateProductRequest.getName())) {
                throw new ConflictException("Already Name Exists.");
            }
        }
        productMapper.updateProduct(updateProductRequest, product);
        productRepository.save(product);
    }

    // Delete Mapping -
    @Override
    public void deleteProduct(UUID productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new ResourceNotFoundException("Product Not Found");  // Check product is present
        }
    }

    @Override
    public Page<ProductResponse> getAllProductsByName(String search, Pageable pageable) {
        return  productRepository.findByName(search.toLowerCase(),pageable).map(product->productMapper.toProductResponse(product));
    }
    
}
