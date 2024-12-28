package com.ssafy12.tripchoice.auth.jwt;

import java.time.Instant;
import java.util.List;

public record JwtClaims(Long tokenId, Long userId, JwtType type, List<String> roles, Instant expiresAt) {
}
