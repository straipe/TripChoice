package com.ssafy12.tripchoice.tripplan.plan.dto.request;

import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanUpdateRequestDto(@NotEmpty String title,
                                   @NotEmpty String summary,
                                   String imgUrl,
                                   @NotNull List<ScheduleCreateDto> schedules) {
}
