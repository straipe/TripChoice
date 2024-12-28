package com.ssafy12.tripchoice.hotplace.dto.response;

import lombok.Builder;

@Builder
public record HotPlaceDto(
                          Long id,
                          Long userId,
                          String userName,
                          String title,
                          String summary,
                          String contentType,
                          String address,
                          Double longitude,
                          Double latitude,
                          String imgUrl) {
}
