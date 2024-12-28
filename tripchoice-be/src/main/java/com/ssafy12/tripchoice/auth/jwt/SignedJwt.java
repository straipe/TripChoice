package com.ssafy12.tripchoice.auth.jwt;

import lombok.Builder;

@Builder
public record SignedJwt(String value, Long expirySeconds) {
}
