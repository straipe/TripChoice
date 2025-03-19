package com.ssafy12.tripchoice.weather.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record WeatherRequestDto(
        @NotNull Integer X,
        @NotNull Integer Y
) {
}
