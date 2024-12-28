package com.ssafy12.tripchoice.hotplace.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record HotPlaceCreateDto(
        Long userId,
        String title,
        Integer contentTypeId,
        String address,
        String summary,
        Double longitude,
        Double latitude,
        String image) {
}
