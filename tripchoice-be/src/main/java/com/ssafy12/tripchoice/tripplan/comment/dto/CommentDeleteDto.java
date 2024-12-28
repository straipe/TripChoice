package com.ssafy12.tripchoice.tripplan.comment.dto;

import lombok.Builder;

@Builder
public record CommentDeleteDto(Long userId,
                               Long planId,
                               Long commentId) {

}
