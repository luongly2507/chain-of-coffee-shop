package com.app.coffee.payload.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BranchResponse {
    private UUID id;
    private String name;
    private String address;
    private String description;
}
