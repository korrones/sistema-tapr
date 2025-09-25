package com.example.authservice.application.auth;

import com.example.authservice.domain.user.RefreshTokenRepository;
import com.example.authservice.domain.user.vo.RefreshToken;
import com.example.authservice.application.port.TokenService;
import com.example.authservice.interfaces.rest.dto.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenHandler {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenService tokenService;

    public TokenResponse refresh(String refreshTokenHash) {
        Optional<RefreshToken> optToken = refreshTokenRepository.findActiveByHash(refreshTokenHash);
        if (optToken.isEmpty()) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }
        RefreshToken refreshToken = optToken.get();
    com.example.authservice.domain.user.User user = new com.example.authservice.domain.user.User();
    user.setId(refreshToken.getUserId());
    TokenService.TokenPair tokenPair = tokenService.issue(user);
    refreshTokenRepository.revoke(refreshToken.getId());
    RefreshToken novoRefresh = new RefreshToken(null, tokenPair.refreshToken(), LocalDateTime.now().plusDays(7), false, refreshToken.getUserId());
    refreshTokenRepository.save(novoRefresh);
    return new TokenResponse(tokenPair.accessToken(), tokenPair.refreshToken(), tokenPair.expiresInSeconds());
    }

    public void logout(String refreshTokenHash) {
        Optional<RefreshToken> optToken = refreshTokenRepository.findActiveByHash(refreshTokenHash);
        optToken.ifPresent(token -> refreshTokenRepository.revoke(token.getId()));
    }
}