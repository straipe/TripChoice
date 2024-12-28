package com.ssafy12.tripchoice.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignupRequestDto(@Email @NotBlank String email,
                               @NotBlank String password,
                               @NotBlank String name,
                               String image) {
}
