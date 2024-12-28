package com.ssafy12.tripchoice.auth.dto;

import lombok.Builder;

@Builder
public record Login(String email, String password) {
}
