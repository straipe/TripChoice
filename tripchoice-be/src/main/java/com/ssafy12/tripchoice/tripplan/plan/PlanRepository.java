package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    @Override
    @EntityGraph(attributePaths = {"user", "schedules"})
    Optional<Plan> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"user", "schedules"})
    List<Plan> findAll();

    @Override
    @EntityGraph(attributePaths = {"user", "schedules"})
    Page<Plan> findAll(Pageable pageable);
}
