package com.app.coffee.payload.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.app.coffee.entity.User;

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
    private List<TagResponse> tags;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
    private List<User> users;
}
