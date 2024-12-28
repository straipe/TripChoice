package com.ssafy12.tripchoice.attraction.dto.response;

import lombok.Builder;

@Builder
public record AttractionDto(Integer no,
                            String title,
                            String contentType,
                            String imgUrl,
                            String address,
                            Double longitude,
                            Double latitude) {
}
