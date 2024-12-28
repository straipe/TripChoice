package com.ssafy12.tripchoice.user.dto;

import lombok.Builder;

@Builder
public record SignupDto(String email, String password, String name, String image) {
}
