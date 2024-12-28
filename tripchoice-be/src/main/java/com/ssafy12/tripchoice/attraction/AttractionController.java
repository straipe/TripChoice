package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import com.ssafy12.tripchoice.attraction.dto.response.AttractionDto;
import com.ssafy12.tripchoice.attraction.dto.response.AttractionTop4ResponseDto;
import com.ssafy12.tripchoice.attraction.dto.response.GugunDto;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.common.dto.PageResponseDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.PlanSearchDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/attractions")
@RequiredArgsConstructor
public class AttractionController {

    private final AttractionService attractionService;

    @GetMapping("/{attractionId}")
    public ResponseEntity<AttractionDto> getDetail(@PathVariable(name = "attractionId") Integer attractionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attractionService.getById(attractionId));
    }

//    @GetMapping("/search")
    @GetMapping
    public ResponseEntity<PageResponseDto> search(@Valid AttractionSearchDto request) {
        log.info("request = {}",request);
        var page = attractionService.search(request);
        return ResponseEntity.ok()
                .body(PageResponseDto.builder()
                        .currentPage(page.getNumber())
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .content(page.getContent())
                        .build());
    }

    @GetMapping("/top4")
    public ResponseEntity<AttractionTop4ResponseDto> getTop4Attr(){
        var attractions = attractionService.searchTop4();
        return ResponseEntity.ok()
                .body(AttractionTop4ResponseDto.builder()
                        .content(attractions)
                        .build());
    }

    @GetMapping("/areas/{sidoCode}")
    public ResponseEntity<List<GugunDto>> getGuguns(@PathVariable(name = "sidoCode") Long sidoCode) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attractionService.getGuguns(sidoCode));
    }

    @GetMapping("/auto")
    public ResponseEntity<List<String>> autoComplete(@RequestParam String keyword) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(attractionService.autoComplete(keyword));
    }
}