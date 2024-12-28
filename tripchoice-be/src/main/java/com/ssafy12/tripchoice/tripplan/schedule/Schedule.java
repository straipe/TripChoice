package com.ssafy12.tripchoice.tripplan.schedule;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@Table(name = "schedules")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long planId;

    private Integer sequence;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleAttraction> scheduleAttractions = new ArrayList<>();

    @Builder
    private Schedule(Long userId, Long planId, Integer sequence) {
        this.userId = userId;
        this.planId = planId;
        this.sequence = sequence;
    }

    public void scheduleAttractions(List<Integer> attractionIds) {
        log.info("Schedule attractions = {}",attractionIds);
        scheduleAttractions.clear();
        List<ScheduleAttraction> newScheduleAttractions = new ArrayList<>();
        int seq = 0;
        for (Integer attractionId : attractionIds) {
            var saOptional = scheduleAttractions.stream()
                    .filter(sa -> sa.getAttractionId().equals(attractionId))
                    .findAny();

            if (saOptional.isPresent()) {
                ScheduleAttraction sa = saOptional.get();
                sa.setSequence(seq++);
                newScheduleAttractions.add(sa);
            } else {
                newScheduleAttractions.add(ScheduleAttraction.builder()
                        .schedule(this)
                        .attractionId(attractionId)
                        .sequence(seq)
                        .build());
            }
        }

        this.scheduleAttractions.addAll(newScheduleAttractions);
    }
}
