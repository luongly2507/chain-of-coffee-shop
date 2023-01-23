package com.app.coffee.payload.request;



import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCategoryRequest {
    @NotBlank(message = "Name is not blank.")
    private String name;
    private String description;
}
