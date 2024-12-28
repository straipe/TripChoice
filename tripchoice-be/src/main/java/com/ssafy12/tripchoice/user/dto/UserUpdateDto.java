package com.ssafy12.tripchoice.user.dto;

import lombok.Builder;

@Builder
public record UserUpdateDto(Long userId, String password, String name, String role, String image) {
}
