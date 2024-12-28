package com.ssafy12.tripchoice.tripplan.plan.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record PlanTop4ResponseDto (List<PlanDto> content){

}
