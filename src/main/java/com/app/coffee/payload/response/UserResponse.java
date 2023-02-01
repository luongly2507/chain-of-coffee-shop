package com.app.coffee.payload.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String gender;
    private String telephone;
    private String address;
    private String email;
    private LocalDate birthday;
    private Collection<String> roles;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private LocalDateTime createdAt;
    private String lastModifiedBy;
}
