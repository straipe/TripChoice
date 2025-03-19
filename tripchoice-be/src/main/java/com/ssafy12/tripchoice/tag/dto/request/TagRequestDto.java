package com.ssafy12.tripchoice.tag.dto.request;

import jakarta.validation.constraints.NotNull;

public record TagRequestDto (
        @NotNull String model,
        @NotNull String address
){ }
