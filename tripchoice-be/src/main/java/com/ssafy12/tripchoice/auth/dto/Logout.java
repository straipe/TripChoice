package com.ssafy12.tripchoice.auth.dto;

import lombok.Builder;

@Builder
public record Logout(String accessToken, String refreshToken) {
}
