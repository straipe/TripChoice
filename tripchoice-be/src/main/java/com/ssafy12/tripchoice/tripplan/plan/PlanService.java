package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.attraction.AttractionService;
import com.ssafy12.tripchoice.attraction.dto.response.AttractionDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.*;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDetailDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDto;
import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import com.ssafy12.tripchoice.tripplan.schedule.ScheduleService;
import com.ssafy12.tripchoice.tripplan.schedule.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanService {

    private final PlanCommandService planCommandService;
    private final PlanQueryService planQueryService;
    private final AttractionService attractionService;
    private final ScheduleService scheduleService;

    @Transactional
    public void create(PlanCreateDto dto) {
        planCommandService.create(dto);
    }

    @Transactional
    public void update(PlanUpdateDto dto) {
        planCommandService.update(dto);
    }

    @Transactional
    public void delete(PlanDeleteDto dto) {
        planCommandService.delete(dto);
    }

    public PlanDetailDto getPlan(Long planId) {
        Plan plan = planQueryService.getPlan(planId);
        List<Long> scheduleIds = plan.getSchedules().stream().map(Schedule::getId).toList();
        System.out.println("스케줄 아이디 목록: "+scheduleIds);
        List<List<AttractionDto>> schedules = new ArrayList<>();
        for (Long scheduleId : scheduleIds) {
            schedules.add(scheduleService.getSchedule(scheduleId)
                    .attractions().stream()
                    .map(attractionService::getById)
                    .toList());
        }
        return PlanDetailDto.builder()
                .userId(plan.getUser().getId().toString())
                .userName(plan.getUser().getName())
                .id(planId)
                .title(plan.getTitle())
                .summary(plan.getSummary())
                .imgUrl(plan.getImgUrl())
                .schedules(schedules)
                .build();
    }

    public Page<PlanDto> search(PlanSearchDto dto) {
        return planQueryService.search(dto).map(this::toDto);
    }

    public List<PlanDto> searchTop4() { return planQueryService.searchTop4().stream()
            .map(this::toDto).collect(Collectors.toList()); }

    private PlanDto toDto(Plan plan) {
        return PlanDto.builder()
                .id(plan.getId().toString())
                .userId(plan.getUser().getId().toString())
                .userName(plan.getUser().getName())
                .title(plan.getTitle())
                .summary(plan.getSummary())
                .imgUrl(plan.getImgUrl())
                .build();
    }
}