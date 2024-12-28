package com.ssafy12.tripchoice.tripplan.plan;

import com.ssafy12.tripchoice.common.auditing.BaseTimeEntity;
import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import com.ssafy12.tripchoice.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "plans")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @Setter(AccessLevel.PACKAGE)
    private String title;

    @Setter(AccessLevel.PACKAGE)
    private String summary;

    @Setter(AccessLevel.PACKAGE)
    private String imgUrl;

    @OneToMany(mappedBy = "planId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @Builder
    private Plan(User user, String title, String summary, String imgUrl) {
        this.user = user;
        this.title = title;
        this.summary = summary;
        this.imgUrl = imgUrl;
    }

    public void updateSchedules(List<ScheduleCreateDto> scheduleCreateDtos) {
        for( ScheduleCreateDto scheduleCreateDto : scheduleCreateDtos ) {
            System.out.println("updateSchedules: " + scheduleCreateDto);
            System.out.println(scheduleCreateDto.attractionIds());

        }
        this.schedules.clear();
        scheduleCreateDtos.stream()
                .map(ScheduleCreateDto::attractionIds)
                .forEach(this::addSchedule);
    }

    public void addSchedule(List<Integer> attractionIds) {
        Schedule schedule = Schedule.builder()
                .planId(this.id)
                .userId(this.user.getId())
                .sequence(this.schedules.size())
                .build();
        schedule.scheduleAttractions(attractionIds);
        this.schedules.add(schedule);
    }
}
