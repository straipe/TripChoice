package com.ssafy12.tripchoice.tripplan.plan.dto.response;

import lombok.Builder;

@Builder
public record PlanDto(String id,
                      String userId,
                      String userName,
                      String title,
                      String summary,
                      String imgUrl) {
}
