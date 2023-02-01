package com.app.coffee.service;

import java.util.Optional;
import java.util.UUID;

import com.app.coffee.entity.RefreshToken;

public interface RefreshTokenService {
    public Optional<RefreshToken> findByToken(String token);
    public RefreshToken createRefreshToken(UUID userId);
    public RefreshToken verifyExpiration(RefreshToken token);
    public int deleteByUserId(UUID userId);
}
