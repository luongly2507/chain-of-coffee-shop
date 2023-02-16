package com.app.coffee.payload.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor   
public class UpdateBranchRequest {
    @NotBlank(message = "Name is not blank.")
    @Size(max = 100, min = 1)
    private String name;
    private String address;
    private String description;
}
