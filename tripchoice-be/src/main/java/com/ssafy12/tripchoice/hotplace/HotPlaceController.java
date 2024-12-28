package com.ssafy12.tripchoice.hotplace;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.board.dto.BoardSearchDto;
import com.ssafy12.tripchoice.board.dto.response.BoardDto;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.common.dto.PageResponseDto;
import com.ssafy12.tripchoice.hotplace.dto.HotPlaceCreateDto;
import com.ssafy12.tripchoice.hotplace.dto.HotPlaceSearchDto;
import com.ssafy12.tripchoice.hotplace.dto.request.HotPlaceCreateRequestDto;
import com.ssafy12.tripchoice.hotplace.dto.response.HotPlaceDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/hotplaces")
@RequiredArgsConstructor
public class HotPlaceController {

    private final HotPlaceService hotPlaceService;

    @PostMapping
    public ResponseEntity<Void> createHotPlace(@AuthenticatedUserId Long userId, @RequestBody HotPlaceCreateRequestDto request) {
        log.info("create hot place request = {}", request);
        hotPlaceService.createHotPlace(HotPlaceCreateDto.builder()
                .userId(userId)
                .title(request.title())
                .contentTypeId(request.contentTypeId())
                .address(request.address())
                .summary(request.summary())
                .longitude(request.longitude())
                .latitude(request.latitude())
                .image(request.image())
                .build());
        return ResponseEntity.status(CREATED).build();
    }

    // 단일 조회
    @GetMapping("/{hotPlaceId}")
    public ResponseEntity<HotPlaceDto> getHotPlace(@PathVariable(name = "hotPlaceId") Long hotPlaceId) {
        return ResponseEntity.ok()
                .body(hotPlaceService.getHotPlace(hotPlaceId));
    }

    // 페이징
    @GetMapping
    public ResponseEntity<PageResponseDto> getPage(@Valid HotPlaceSearchDto request) {
        log.info("request = {}",request);
        var page = hotPlaceService.search(request);
        return ResponseEntity.ok()
                .body(PageResponseDto.builder()
                        .currentPage(page.getNumber()+1)
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .content(page.getContent())
                        .build());
    }
}
