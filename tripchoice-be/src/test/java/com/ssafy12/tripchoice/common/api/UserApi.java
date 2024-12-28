package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.user.dto.SignupRequestDto;
import com.ssafy12.tripchoice.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class UserApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions signup(SignupRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/users/signup")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions getUser() throws Exception {
        return mockMvc.perform(get("/api/users/me")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions update(UserUpdateRequestDto request) throws Exception {
        return mockMvc.perform(patch("/api/users/me")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions signout() throws Exception {
        return mockMvc.perform(delete("/api/users/me")
                .header(authProps.header, String.join(" ", authProps.scheme, AccessTokenHolder.accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }
}
