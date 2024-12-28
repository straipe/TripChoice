package com.ssafy12.tripchoice.tripplan.comment;

import com.ssafy12.tripchoice.common.auditing.BaseTimeEntity;
import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long planId;

    private String content;

    @Builder
    private Comment(Long userId, Long planId, String content) {
        this.userId = userId;
        this.planId = planId;
        this.content = content;
    }
}
