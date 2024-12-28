package com.ssafy12.tripchoice.board.dto.request;

import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record BoardCreateRequestDto(@NotEmpty String title,
                                    @NotEmpty String content) {
}
