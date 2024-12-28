package com.ssafy12.tripchoice.auth.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(String accessToken, String refreshToken) {
}
