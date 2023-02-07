package com.app.coffee.payload.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagResponse {
    private UUID id;
    private BranchResponse branch;
    private String name;
    private String status;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
}
