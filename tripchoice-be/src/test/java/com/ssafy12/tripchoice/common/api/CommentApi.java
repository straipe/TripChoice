package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.tripplan.comment.dto.request.CommentCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class CommentApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions create(Long planId, CommentCreateRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/plans/{planId}/comments", planId)
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions deleteComment(Long planId, Long commentId) throws Exception {
        return mockMvc.perform(delete("/api/plans/{planId}/comments/{commentId}", planId, commentId)
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}
