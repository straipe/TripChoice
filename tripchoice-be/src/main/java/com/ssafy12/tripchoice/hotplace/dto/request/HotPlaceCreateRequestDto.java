package com.ssafy12.tripchoice.hotplace.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record HotPlaceCreateRequestDto(
        @Schema(description = "핫플레이스 제목", example = "Beautiful Beach")
        @NotEmpty String title,
        @Schema(description = "콘텐츠 타입 ID", example = "1")
        @NotNull Integer contentTypeId,
        @Schema(description = "주소", example = "123 Ocean Drive")
        String address,
        @Schema(description = "요약", example = "A serene and beautiful beach.")
        String summary,
        @Schema(description = "경도", example = "-123.45")
        Double longitude,
        @Schema(description = "위도", example = "45.67")
        Double latitude,
        @Schema(description = "이미지 파일")
        String image) {
}
