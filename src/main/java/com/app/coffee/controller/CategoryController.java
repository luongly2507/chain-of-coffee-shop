package com.app.coffee.controller;

import java.net.URI;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.app.coffee.payload.request.CreateCategoryRequest;
import com.app.coffee.payload.request.UpdateCategoryRequest;
import com.app.coffee.payload.response.CategoryResponse;
import com.app.coffee.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
@Tag(
    name = "Category API",
    description = "APIs related to the Category of Products in the application."
)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    //Get Mapping
    @Operation(description = "Get All Categories.", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))), responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(description = "Get All Categories Pagination and Sort.", responses = {
        @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))), responseCode = "200")
    })
    @GetMapping("/page")
    public ResponseEntity<?> getAllCategories(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(categoryService.getAllCategories(pageable));
    } 

    @Operation(description = "Get Category By Category Id.", responses = {
        @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))), responseCode = "200")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable @Parameter(name = "Category ID") UUID categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }

    // Post Mapping
    @Operation(description = "Create Category", responses = {
        @ApiResponse(responseCode = "201")
    })
    @PostMapping
    public  ResponseEntity<?> createCategory(@RequestBody @Valid CreateCategoryRequest createCategoryRequest){
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryService.createCategory(createCategoryRequest).getId()).toUri();
        return ResponseEntity.created(location).build();
    }
  
    // Put Mapping
    @Operation(description = "Update Category By Category Id.", responses = {
        @ApiResponse(responseCode = "204")
    })

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable @Parameter(name=" Category ID") UUID categoryId, @RequestBody @Valid UpdateCategoryRequest updateCategoryRequest){
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return ResponseEntity.noContent().build();
    }
    // Delete Mapping
    @Operation(description = "Delete Category By Category Id.", responses = {
        @ApiResponse(responseCode = "204")
    })

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable @Parameter(name = "Category ID") UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }



}
