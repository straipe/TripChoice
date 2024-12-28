package com.ssafy12.tripchoice.board;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.board.dto.BoardCreateDto;
import com.ssafy12.tripchoice.board.dto.BoardDeleteDto;
import com.ssafy12.tripchoice.board.dto.BoardSearchDto;
import com.ssafy12.tripchoice.board.dto.BoardUpdateDto;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.board.dto.response.BoardDto;
import com.ssafy12.tripchoice.common.dto.PageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticatedUserId Long userId,
                                       @RequestBody @Valid BoardCreateRequestDto request) {
        boardService.create(BoardCreateDto.builder()
                .userId(userId)
                .title(request.title())
                .content(request.content())
                .build());
        return ResponseEntity.status(CREATED).build();
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<Void> update(@AuthenticatedUserId Long userId,
                                       @PathVariable(name = "boardId") Long boardId,
                                       @RequestBody @Valid BoardUpdateRequestDto request) {
        boardService.update(BoardUpdateDto.builder()
                .userId(userId)
                .boardId(boardId)
                .title(request.title())
                .content(request.content())
                .build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@AuthenticatedUserId Long userId,
                                       @PathVariable(name = "boardId") Long boardId) {
        boardService.delete(BoardDeleteDto.builder()
                .userId(userId)
                .boardId(boardId)
                .build());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto> getPlan(@PathVariable(name = "boardId") Long boardId) {
        return ResponseEntity.ok()
                .body(boardService.getById(boardId));
    }

    @GetMapping
    public ResponseEntity<PageResponseDto> getPage(@Valid BoardSearchDto request) {
        Page<BoardDto> page = boardService.search(BoardSearchDto.builder()
                .page(request.page())
                .size(request.size())
                .condition(request.condition())
                .keyword(request.keyword())
                .build());
        return ResponseEntity.ok()
                .body(PageResponseDto.builder()
                        .currentPage(page.getNumber())
                        .size(page.getSize())
                        .totalPage(page.getTotalPages())
                        .content(page.getContent())
                        .build());
    }

//    @GetMapping("/search")
//    public ResponseEntity<BoardPageResponseDto> search(@Valid PageRequestDto request) {
//        Page<BoardDto> currentPage = boardService.search(BoardSearchDto.builder()
//                .currentPage(request.currentPage())
//                .size(request.size())
//                .condition(BoardSearchCondition.valueOf(request.condition()))
//                .keyword(request.keyword())
//                .build());
//        return ResponseEntity.ok()
//                .body(BoardPageResponseDto.builder()
//                        .currentPage(currentPage.getNumber())
//                        .size(currentPage.getSize())
//                        .totalPage(currentPage.getTotalPages())
//                        .content(currentPage.getContent())
//                        .build());
//    }
}
