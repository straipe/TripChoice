package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class BoardApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions create(BoardCreateRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/boards")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions updateBoard(Long boardId, BoardUpdateRequestDto request) throws Exception {
        return mockMvc.perform(put("/api/boards/{boardId}",boardId)
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions deleteBoard(Long boardId) throws Exception {
        return mockMvc.perform(delete("/api/boards/{boardId}", boardId)
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    public ResultActions getBoard(Long boardId) throws Exception {
        return mockMvc.perform(get("/api/boards/{boardId}", boardId)
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    public ResultActions getPage(PageRequestDto request) throws Exception {
        return mockMvc.perform(get("/api/boards")
                .param("page", request.page().toString())
                .param("size", request.size().toString())
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}
