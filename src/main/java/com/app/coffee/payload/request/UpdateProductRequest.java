package com.app.coffee.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor  
public class UpdateProductRequest {
    private String categoryID;
    private Double price;
    private String name;
    private String description;
}
