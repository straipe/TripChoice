package com.ssafy12.tripchoice.fixture;


import com.ssafy12.tripchoice.auth.dto.request.LoginRequestDto;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.dto.SignupRequestDto;

public class UserFixture extends User {

    public static String DEFAULT_EMAIL = "test@mail.com";
    public static String DEFAULT_PASSWORD = "password";
    public static String DEFAULT_NAME = "username";

    public static SignupRequestDto.SignupRequestDtoBuilder aSignupRequestDtoBuilder() {
        return SignupRequestDto.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .name(DEFAULT_NAME);
    }

    public static SignupRequestDto aSignupRequestDto() {
        return aSignupRequestDtoBuilder().build();
    }

    public static LoginRequestDto.LoginRequestDtoBuilder aLoginRequestDtoBuilder(){
        return LoginRequestDto.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD);
    }

    public static LoginRequestDto aLoginRequestDto(){
        return aLoginRequestDtoBuilder().build();
    }
}
