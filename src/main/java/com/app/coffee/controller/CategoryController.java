package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CATEGORY')")
    public ResponseEntity<?> getAllCategories() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/page")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CATEGORY')")
    public ResponseEntity<?> getAllCategories(Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    }
    @GetMapping("/search")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CATEGORY')")
    public ResponseEntity<?> getAllCategoriesByName(@RequestParam String key, Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategoriesByName(key,pageable));
    }
    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_READ_CATEGORY')")
    public ResponseEntity<?> getCategoryById(
            @PathVariable UUID categoryId) {
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('PRIVILEGE_CREATE_CATEGORY')")
    public ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryService.createCategory(createCategoryRequest).getId()).toUri();
        System.out.println(location);
        return ResponseEntity.created(location).build();
    }

    @PutMapping("{categoryId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE_CATEGORY')")
    public ResponseEntity<?> updateCategory(
            @PathVariable UUID categoryId,
            @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{categoryId}")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE_CATEGORY')")
    public ResponseEntity<?> deleteCategory(
            @PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }

}
