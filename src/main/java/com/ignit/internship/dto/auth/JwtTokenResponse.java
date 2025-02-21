package com.ignit.internship.dto.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class JwtTokenResponse {

    private String token;

    private Long expirationTime;

    private LocalDateTime issuedAt;

    private LocalDateTime expirationDate;

    public JwtTokenResponse(String token, Long expirationTime) {
        this.token = token;
        this.expirationTime = expirationTime;
        this.issuedAt = LocalDateTime.now(ZoneId.of("UTC"));
        this.expirationDate = LocalDateTime.now(ZoneId.of("UTC")).plus(Duration.ofMillis(expirationTime));
    }

    public String getToken() {
        return token;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}

