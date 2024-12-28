package com.ssafy12.tripchoice.tripplan.comment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentCreateRequestDto(@NotNull String content) {

}
