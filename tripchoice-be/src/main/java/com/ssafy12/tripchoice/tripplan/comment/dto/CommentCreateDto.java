package com.ssafy12.tripchoice.tripplan.comment.dto;

import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CommentCreateDto(Long userId,
        Long planId,
        String content) {

}
