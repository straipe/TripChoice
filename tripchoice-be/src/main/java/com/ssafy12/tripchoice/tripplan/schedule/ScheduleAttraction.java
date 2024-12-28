package com.ssafy12.tripchoice.tripplan.schedule;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "schedule_attractions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleAttraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.PROTECTED)
    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheduleId")
    private Schedule schedule;

    @JoinColumn(name = "attractionNo")
    private Integer attractionId;

    @Builder
    public ScheduleAttraction(Integer sequence, Schedule schedule, Integer attractionId) {
        this.sequence = sequence;
        this.schedule = schedule;
        this.attractionId = attractionId;
    }
}
