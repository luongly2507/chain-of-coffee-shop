package com.app.coffee.payload.request;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateUserRequest {
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    private String name;
    private String gender;
    private Set<String> role;
    private String telephone;
    private String address;
    private UUID branch;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private LocalDate birthday;
}
