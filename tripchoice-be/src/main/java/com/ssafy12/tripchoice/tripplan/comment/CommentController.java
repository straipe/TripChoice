package com.ssafy12.tripchoice.tripplan.comment;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.tripplan.comment.dto.CommentCreateDto;
import com.ssafy12.tripchoice.tripplan.comment.dto.CommentDeleteDto;
import com.ssafy12.tripchoice.tripplan.comment.dto.request.CommentCreateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/plans/{planId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthenticatedUserId Long userId,
                                       @PathVariable Long planId,
                                       @RequestBody @Valid CommentCreateRequestDto request) {
        commentService.create(CommentCreateDto.builder()
                .userId(userId)
                .planId(planId)
                .content(request.content())
                .build());
        return ResponseEntity.status(CREATED).build();
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(@AuthenticatedUserId Long userId,
                                       @PathVariable Long planId,
                                       @PathVariable Long commentId) {
        commentService.delete(CommentDeleteDto.builder()
                .userId(userId)
                .planId(planId)
                .commentId(commentId)
                .build());
        return ResponseEntity.noContent().build();
    }
}
