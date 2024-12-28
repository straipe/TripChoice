package com.ssafy12.tripchoice.tripplan.schedule;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Override
    @EntityGraph(attributePaths = {"scheduleAttractions"})
    Optional<Schedule> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"scheduleAttractions"})
    List<Schedule> findAll();

    @EntityGraph(attributePaths = {"scheduleAttractions"})
    List<Schedule> findAllByPlanId(Long planId);
}
