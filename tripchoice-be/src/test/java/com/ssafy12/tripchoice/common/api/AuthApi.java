package com.ssafy12.tripchoice.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy12.tripchoice.auth.config.AuthProps;
import com.ssafy12.tripchoice.auth.dto.request.AuthRequestDto;
import com.ssafy12.tripchoice.auth.dto.request.LoginRequestDto;
import com.ssafy12.tripchoice.auth.dto.response.AuthResponse;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Component
@RequiredArgsConstructor
public class AuthApi {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthProps authProps;

    public ResultActions login(LoginRequestDto request) throws Exception {
        return mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    public ResultActions refresh(String refreshToken) throws Exception {
        return mockMvc.perform(post("/api/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthRequestDto.builder().refreshToken(refreshToken).build())));
    }

    public ResultActions refreshWithoutCookie() throws Exception {
        return mockMvc.perform(post("/api/auth/refresh"));
    }

    public ResultActions logout(String accessToken, String refreshToken) throws Exception {
        return mockMvc.perform(post("/api/auth/logout")
                .header(authProps.header, String.join(" ", authProps.scheme, accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthRequestDto.builder().refreshToken(refreshToken).build())));
    }

    public ResultActions autnenticate(String accessToken) throws Exception {
        return mockMvc.perform(get("/api/auth/authentication")
                .header(authProps.header, String.join(" ", authProps.scheme, accessToken)));
    }

    public ResultActions authorize(String accessToken) throws Exception {
        return mockMvc.perform(get("/api/auth/authorization")
                .header(authProps.header, String.join(" ", authProps.scheme, accessToken)));
    }

    public AuthResult loginAndGetAuthResults(LoginRequestDto request) throws Exception {
        return getAuthResult(login(request));
    }

    private AuthResult getAuthResult(ResultActions result) throws Exception {
        MockHttpServletResponse response = result.andReturn().getResponse();
        var json = response.getContentAsString();
        AuthResponse authResponse = objectMapper.readValue(json, AuthResponse.class);
        var accessToken = authResponse.accessToken();
        var refreshToken = authResponse.refreshToken();
        return new AuthResult(accessToken, refreshToken);
    }

    public record AuthResult(String accessToken, String refreshToken) {
    }
}
