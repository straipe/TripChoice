package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.dto.request.PlanUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class AttractionApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions getPage(AttractionSearchDto request) throws Exception {
        return mockMvc.perform(get("/api/attractions")
                .param("page", request.page().toString())
                .param("size", request.size().toString())
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}
