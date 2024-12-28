package com.ssafy12.tripchoice.tripplan.schedule;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.common.dto.PageResponseDto;
import com.ssafy12.tripchoice.tripplan.plan.PlanService;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanCreateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanDeleteDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanUpdateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanUpdateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDetailDto;
import com.ssafy12.tripchoice.tripplan.schedule.dto.ScheduleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleDto> getPlan(@PathVariable(name = "scheduleId") Long scheduleId) {
        return ResponseEntity.ok()
                .body(scheduleService.getSchedule(scheduleId));
    }
}
