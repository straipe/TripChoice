package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.common.dto.PageResponseDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanCreateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanDeleteDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanUpdateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanUpdateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanDetailDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.response.PlanTop4ResponseDto;
import com.ssafy12.tripchoice.tripplan.schedule.ScheduleService;
import com.ssafy12.tripchoice.tripplan.schedule.dto.ScheduleDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticatedUserId Long userId, @RequestBody @Valid PlanCreateRequestDto request) {
        planService.create(PlanCreateDto.builder()
                .userId(userId)
                .title(request.title())
                .summary(request.summary())
                .imgUrl(request.imgUrl())
                .scheduleCreateDtos(request.schedules())
                .build());
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{planId}")
    public ResponseEntity<Void> update(@AuthenticatedUserId Long userId,
                                       @PathVariable(name = "planId") Long planId,
                                       @RequestBody @Valid PlanUpdateRequestDto request) {
        planService.update(PlanUpdateDto.builder()
                .userId(userId)
                .planId(planId)
                .title(request.title())
                .summary(request.summary())
                .imgUrl(request.imgUrl())
                .scheduleCreateDtos(request.schedules())
                .build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{planId}")
    public ResponseEntity<Void> delete(@AuthenticatedUserId Long userId,
                                       @PathVariable(name = "planId") Long planId) {
        planService.delete(PlanDeleteDto.builder()
                .userId(userId)
                .planId(planId)
                .build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{planId}")
    public ResponseEntity<PlanDetailDto> getPlan(@PathVariable(name = "planId") Long planId) {
        return ResponseEntity.ok()
                .body(planService.getPlan(planId));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto> getPage(@Valid PlanSearchDto request) {
        var page = planService.search(request);
        return ResponseEntity.ok()
                .body(PageResponseDto.builder()
                        .currentPage(page.getNumber())
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .content(page.getContent())
                        .build());
    }

    @GetMapping("/top4")
    public ResponseEntity<PlanTop4ResponseDto> getTop4Plan(){
        var plans = planService.searchTop4();
        return ResponseEntity.ok()
                .body(PlanTop4ResponseDto.builder()
                        .content(plans)
                        .build());
    }

    @GetMapping("/{planId}/schedules")
    public ResponseEntity<List<ScheduleDto>> getSchedules(@PathVariable(name = "planId") Long planId) {
        log.info("schedules for plan = {}", planId);
        return ResponseEntity.ok()
                .body(scheduleService.getAllSchedulesByPlanId(planId));
    }
}
