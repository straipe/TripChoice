package com.ssafy12.tripchoice.attraction.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AttractionTop4ResponseDto(List<AttractionDto> content) {

}
