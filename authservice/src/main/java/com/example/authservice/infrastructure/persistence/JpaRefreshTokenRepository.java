package com.example.authservice.infrastructure.persistence;

import com.example.authservice.domain.user.RefreshTokenRepository;
import com.example.authservice.domain.user.vo.RefreshToken;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    // Aqui você implementaria a integração real com o banco usando JPA/Hibernate
    // Exemplo simplificado para estrutura inicial
    private final Map<Long, RefreshToken> tokens = new HashMap<>();
    private long idCounter = 1;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        if (refreshToken.getId() == null) {
            refreshToken = new RefreshToken(idCounter++, refreshToken.getTokenHash(), refreshToken.getExpiresAt(), false, refreshToken.getUserId());
        }
        tokens.put(refreshToken.getId(), refreshToken);
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findActiveByHash(String tokenHash) {
        return tokens.values().stream()
                .filter(t -> t.getTokenHash().equals(tokenHash) && !t.isRevoked() && t.getExpiresAt().isAfter(java.time.LocalDateTime.now()))
                .findFirst();
    }

    @Override
    public void revoke(Long id) {
        RefreshToken token = tokens.get(id);
        if (token != null) {
            token.setRevoked(true);
        }
    }

    @Override
    public void deleteById(Long id) {
        tokens.remove(id);
    }
}