package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.hotplace.dto.request.HotPlaceCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Component
@RequiredArgsConstructor
public class HotPlaceApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions create(HotPlaceCreateRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/hotplaces")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }
}
