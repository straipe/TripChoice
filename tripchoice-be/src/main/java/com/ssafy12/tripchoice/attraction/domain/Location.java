package com.ssafy12.tripchoice.attraction.domain;

import jakarta.persistence.Embeddable;
import lombok.Builder;

@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;

    protected Location() {} // JPA 기본 생성자 필요

    @Builder
    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double latitude() {
        return latitude;
    }

    public Double longitude() {
        return longitude;
    }
}

