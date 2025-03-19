package com.ssafy12.tripchoice.fixture;


import com.ssafy12.tripchoice.tripplan.plan.Plan;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanCreateRequestDto.PlanCreateRequestDtoBuilder;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanUpdateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanUpdateRequestDto.PlanUpdateRequestDtoBuilder;
import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;

import java.util.ArrayList;
import java.util.List;

public class PlanFixture extends Plan {

    public static String DEFAULT_TITLE = "My Plan Title";
    public static String DEFAULT_SUMMARY = "Enjoy my plan 👍";
    public static String DEFAULT_UPDATE_TITLE = "My Plan Title pdate";
    public static String DEFAULT_UPDATE_SUMMARY = "Enjoy my plan update 👍";
    public static List<Integer> DEFAULT_ATTRACTION_IDS = List.of(1, 2, 3);

    public static PlanCreateRequestDto aPlanCreateRequestDto() {
        return aPlanCreateRequestDtoBuilder().build();
    }

    public static PlanCreateRequestDto aPlanCreateRequestDtoWithNSchedules(int nSchedule) {
        List<ScheduleCreateDto> scheduleCreateDtos = new ArrayList<>();
        for (int i = 0; i < nSchedule; i++) {
            scheduleCreateDtos.add(aScheduleCreateRequestDto());
        }
        return aPlanCreateRequestDtoBuilder()
                .schedules(scheduleCreateDtos)
                .build();
    }

    public static ScheduleCreateDto aScheduleCreateRequestDto() {
        return ScheduleCreateDto.builder()
                .attractionIds(DEFAULT_ATTRACTION_IDS)
                .build();
    }

    public static PlanCreateRequestDtoBuilder aPlanCreateRequestDtoBuilder() {
        return PlanCreateRequestDto.builder()
                .title(DEFAULT_TITLE)
                .summary(DEFAULT_SUMMARY)
                .schedules(List.of(
                        aScheduleCreateRequestDto(),
                        aScheduleCreateRequestDto()));
    }

    public static PlanUpdateRequestDtoBuilder aPlanUpdateRequestDtoBuilder() {
        return PlanUpdateRequestDto.builder()
                .title(DEFAULT_UPDATE_TITLE)
                .summary(DEFAULT_UPDATE_SUMMARY)
                .schedules(List.of(
                        aScheduleCreateRequestDto(),
                        aScheduleCreateRequestDto()));
    }

}