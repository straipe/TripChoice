package com.ssafy12.tripchoice.attraction.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sidos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sido {
    @Id
    private Integer no;
    private Integer sidoCode;
    private String sidoName;

    @Builder
    public Sido(Integer no, Integer sidoCode, String sidoName) {
        this.no = no;
        this.sidoCode = sidoCode;
        this.sidoName = sidoName;
    }
}
