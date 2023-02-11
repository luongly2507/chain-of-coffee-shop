package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.CreateProductRequest;
import com.app.coffee.payload.request.UpdateProductRequest;
import com.app.coffee.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@PreAuthorize("hasRole('ADMIN')")
public class ProductRestController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(required = false) String key,Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(key, pageable));
    }
    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestParam MultipartFile image, @Valid CreateProductRequest createProductRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(productService.createProduct(image,createProductRequest).getId()).toUri();
    return ResponseEntity.created(location).build();
    }
   
    @PutMapping("{productId}")
    public ResponseEntity<?> updateProduct(
            @PathVariable UUID productId,
            @RequestParam(value ="image", required=false) MultipartFile image,
            @Valid UpdateProductRequest updateProductRequest) {
        productService.updateProduct(productId,image, updateProductRequest);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
