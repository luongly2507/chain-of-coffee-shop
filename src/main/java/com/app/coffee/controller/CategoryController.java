package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }
    @GetMapping("/search")
    public ResponseEntity<?> getAllCategoriesByName(@RequestParam String key, Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByName(key,pageable));
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(
            @PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryService.createCategory(createCategoryRequest).getId()).toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{categoryId}")
    public ResponseEntity<?> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<?> deleteCategory(
            @PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
