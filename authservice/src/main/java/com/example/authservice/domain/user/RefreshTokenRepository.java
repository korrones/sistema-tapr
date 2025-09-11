package com.example.authservice.domain.user;

import com.example.authservice.domain.user.vo.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findActiveByHash(String tokenHash);
    void revoke(Long id);
    void deleteById(Long id);
}