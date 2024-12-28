package com.ssafy12.tripchoice.tripplan.schedule;

import com.ssafy12.tripchoice.common.exception.http.CustomHttp404Exception;
import com.ssafy12.tripchoice.tripplan.plan.Plan;
import com.ssafy12.tripchoice.tripplan.plan.PlanCommandService;
import com.ssafy12.tripchoice.tripplan.plan.PlanQueryService;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanCreateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanDeleteDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanUpdateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDetailDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDto;
import com.ssafy12.tripchoice.tripplan.schedule.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public List<ScheduleDto> getAllSchedulesByPlanId(Long planId) {
        return scheduleRepository.findAllByPlanId(planId)
                .stream().map(this::toDto)
                .toList();
    }

    public ScheduleDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(CustomHttp404Exception::new);
        log.info("getSchedule: schedule = {}", schedule);
        return toDto(schedule);
    }

    private ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .attractions(schedule.getScheduleAttractions().stream()
                        .map(ScheduleAttraction::getAttractionId)
                        .toList())
                .build();
    }
}