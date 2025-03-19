package com.ssafy12.tripchoice.weather.dto;
import lombok.Builder;

@Builder
public record WeatherResponseDto(
        Integer pop,
        Integer pty,
        String pcp,
        String sno,
        Integer sky,
        Integer tmp,
        Integer reh,
        Float wsd
) {

}
