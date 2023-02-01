package com.app.coffee.payload.response;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;

    @Builder.Default
    private String type = "Bearer";
    private String refreshToken;
    private UUID id;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, UUID id, String email,
            List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.roles = roles;
    }
}