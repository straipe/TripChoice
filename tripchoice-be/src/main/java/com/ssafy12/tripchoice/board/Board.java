package com.ssafy12.tripchoice.board;

import com.ssafy12.tripchoice.common.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boards")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;

    @Builder
    private Board(String title, String content, Long userId, String userName) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.userName = userName;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
