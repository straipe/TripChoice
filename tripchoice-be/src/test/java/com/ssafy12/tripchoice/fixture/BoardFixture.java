package com.ssafy12.tripchoice.fixture;


import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.Plan;

public class BoardFixture extends Plan {

    public static String DEFAULT_TITLE = "DEFAULT BOARD TITLE";
    public static String DEFAULT_CONTENT = "DEFAULT BOARD CONTENT";

    public static BoardCreateRequestDto aBoardCreateRequestDto() {
        return aBoardCreateRequestDtoBuilder().build();
    }

    public static BoardCreateRequestDto.BoardCreateRequestDtoBuilder aBoardCreateRequestDtoBuilder() {
        return BoardCreateRequestDto.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT);
    }
}