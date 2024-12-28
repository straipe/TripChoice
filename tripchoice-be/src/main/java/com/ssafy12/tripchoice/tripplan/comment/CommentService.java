package com.ssafy12.tripchoice.tripplan.comment;

import com.ssafy12.tripchoice.auth.exception.UnauthorizedException;
import com.ssafy12.tripchoice.tripplan.comment.dto.CommentCreateDto;
import com.ssafy12.tripchoice.tripplan.comment.dto.CommentDeleteDto;
import com.ssafy12.tripchoice.tripplan.comment.exception.CommentNotFoundException;
import com.ssafy12.tripchoice.tripplan.plan.PlanRepository;
import com.ssafy12.tripchoice.tripplan.plan.exception.PlanNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.commons.lang3.ObjectUtils.notEqual;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;

    @Transactional
    public void create(CommentCreateDto dto) {
        if (!planRepository.existsById(dto.planId())) {
            throw new PlanNotFoundException();
        }
        commentRepository.save(Comment.builder()
                .userId(dto.userId())
                .planId(dto.planId())
                .content(dto.content())
                .build());
    }

    @Transactional
    public void delete(CommentDeleteDto dto) {
        Comment comment = commentRepository.findById(dto.commentId())
                .orElseThrow(CommentNotFoundException::new);
        if (notEqual(comment.getUserId(), dto.userId())) {
            throw new UnauthorizedException();
        }
        commentRepository.deleteById(dto.commentId());
    }
}