package com.example.authservice.domain.user.vo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class RefreshToken {
    private Long id;
    private String tokenHash;
    private LocalDateTime expiresAt;
    private boolean revoked;
    private UUID userId;

    public RefreshToken(Long id, String tokenHash, LocalDateTime expiresAt, boolean revoked, UUID userId) {
        this.id = id;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.userId = userId;
    }

    public Long getId() { return id; }
    public String getTokenHash() { return tokenHash; }
    public LocalDateTime getExpiresAt() { return expiresAt; }
    public boolean isRevoked() { return revoked; }
    public UUID getUserId() { return userId; }

    public void setRevoked(boolean revoked) { this.revoked = revoked; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}