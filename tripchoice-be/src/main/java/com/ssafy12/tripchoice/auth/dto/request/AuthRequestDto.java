package com.ssafy12.tripchoice.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AuthRequestDto(@NotEmpty String refreshToken) {
}
