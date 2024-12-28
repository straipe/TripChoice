package com.ssafy12.tripchoice.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserUpdateRequestDto(String password, String name, String role, String image) {
}
