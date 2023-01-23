package com.app.coffee.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor   
public class UpdateCategoryRequest {
    @NotBlank(message = "Name is not blank.")
    private String name;
    private String description;
}
