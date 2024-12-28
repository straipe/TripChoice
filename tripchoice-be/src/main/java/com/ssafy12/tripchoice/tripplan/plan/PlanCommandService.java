package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.tripplan.plan.dto.PlanCreateDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanDeleteDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanUpdateDto;
import com.ssafy12.tripchoice.tripplan.plan.exception.*;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.UserRepository;
import com.ssafy12.tripchoice.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.ObjectUtils.notEqual;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PlanCommandService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public void create(PlanCreateDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(UserNotFoundException::new);
        Plan plan = planRepository.save(Plan.builder()
                .user(user)
                .title(dto.title())
                .summary(dto.summary())
                .imgUrl(dto.imgUrl())
                .build());
        plan.updateSchedules(dto.scheduleCreateDtos());
    }

    public void update(PlanUpdateDto dto) {
        Plan plan = getValidatedPlan(dto.userId(), dto.planId());
        plan.setTitle(dto.title());
        plan.setSummary(dto.summary());
        plan.setImgUrl(dto.imgUrl());
        plan.updateSchedules(dto.scheduleCreateDtos());
    }

    public void delete(PlanDeleteDto dto) {
        Plan plan = getValidatedPlan(dto.userId(), dto.planId());
        planRepository.deleteById(plan.getId());
    }

    private Plan getValidatedPlan(Long userId, Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(PlanNotFoundException::new);
        if (notEqual(plan.getUser().getId(), userId)) {
            throw new PlanUnauthorizedException();
        }
        return plan;
    }
}