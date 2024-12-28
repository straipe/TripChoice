package com.ssafy12.tripchoice.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(String message) {
}
