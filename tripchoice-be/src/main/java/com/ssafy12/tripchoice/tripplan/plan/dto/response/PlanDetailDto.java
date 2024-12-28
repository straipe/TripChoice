package com.ssafy12.tripchoice.tripplan.plan.dto.response;

import com.ssafy12.tripchoice.attraction.dto.response.AttractionDto;
import lombok.Builder;

import java.util.List;

@Builder
public record PlanDetailDto(
        String userId,
        String userName,
        Long id,
        String title,
        String summary,
        String imgUrl,
        List<List<AttractionDto>> schedules) {
}
