package com.ssafy12.tripchoice.auth.dto;

import com.ssafy12.tripchoice.auth.jwt.SignedJwt;
import lombok.Builder;

@Builder
public record AuthTokens(SignedJwt forAccess, SignedJwt forRefresh) {
}
