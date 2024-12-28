package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import com.ssafy12.tripchoice.tripplan.plan.exception.PlanNotFoundException;
import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import com.ssafy12.tripchoice.tripplan.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlanQueryService {

    private final PlanRepository planRepository;
    private final PlanQueryRepository planQueryRepository;

    public Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);
    }

    public Page<Plan> search(PlanSearchDto dto) {
        return planQueryRepository.search(dto);
    }

    public List<Plan> searchTop4() { return planQueryRepository.searchTop4(); }
}