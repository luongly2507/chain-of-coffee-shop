package com.app.coffee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.coffee.payload.response.CategoryResponse;
import com.app.coffee.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/category")
@Tag(
    name = "Category API",
    description = "APIs related to the Category of Products in the application."
)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(description = "Get All Categories.", responses = {
            @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))), responseCode = "200")
    })
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    

}
