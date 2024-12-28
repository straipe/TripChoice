package com.ssafy12.tripchoice.tripplan.schedule.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ScheduleDto (List<Integer> attractions){
}
