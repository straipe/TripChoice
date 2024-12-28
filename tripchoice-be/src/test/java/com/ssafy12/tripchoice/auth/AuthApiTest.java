package com.ssafy12.tripchoice.auth;

import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.user.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.ssafy12.tripchoice.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.not;
import static org.springframework.boot.web.server.Cookie.SameSite.STRICT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;

public class AuthApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("로그인 API")
    class Login {
        @Test
        @DisplayName("요청이 성공하면 AccessToken과 RefreshToken이 발급된다")
        void success() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            var request = aLoginRequestDto();

            // when
            ResultActions result = authApi.login(request);

            // then
            result.andExpectAll(
                    status().isOk(),
//                    cookie().exists("refreshToken"),
//                    cookie().maxAge("refreshToken", 259200),
//                    cookie().secure("refreshToken", true),
//                    cookie().httpOnly("refreshToken", true),
//                    cookie().sameSite("refreshToken", STRICT.attributeValue()),
                    jsonPath("$.accessToken").exists(),
                    jsonPath("$.refreshToken").exists(),
                    jsonPath("$.accessToken").isString(),
                    jsonPath("$.refreshToken").isString());
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            static Stream<Arguments> loginFailureWithWrongData() {
                var signupRequest = aSignupRequestDto();
                String email = signupRequest.email();
                String password = signupRequest.password();
                return Stream.of(
                        Arguments.of("wrong" + email, password),
                        Arguments.of(email, "wrong" + password)
                );
            }

            @ParameterizedTest
            @MethodSource
            @DisplayName("이메일 또는 비밀번호가 일치하는 유저가 없는 경우")
            void loginFailureWithWrongData(String email, String password) throws Exception {
                // given
                userApi.signup(aSignupRequestDto());
                var request = aLoginRequestDtoBuilder()
                        .email(email)
                        .password(password)
                        .build();

                // when
                ResultActions result = authApi.login(request);

                // then
                result.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").exists());
            }
        }
    }

    @Nested
    @DisplayName("토큰 리프래시 API")
    class Refresh {
        @Test
        @DisplayName("요청이 성공하면 새로운 AccessToken과 RefreshToken을 발급받는다")
        void success() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            var authResult = authApi.loginAndGetAuthResults(aLoginRequestDto());
            String oldToken = authResult.accessToken();
            String oldRefresh = authResult.refreshToken();

            // when
            ResultActions result = authApi.refresh(oldRefresh);

            // then
            result.andExpectAll(
                    status().isOk(),
//                    cookie().exists("refreshToken"),
//                    cookie().maxAge("refreshToken", 259200),
//                    cookie().secure("refreshToken", true),
//                    cookie().httpOnly("refreshToken", true),
//                    cookie().sameSite("refreshToken", STRICT.attributeValue()),
//                    cookie().value("refreshToken", not(oldRefresh)),
                    jsonPath("$.accessToken").exists(),
                    jsonPath("$.accessToken").isString(),
                    jsonPath("$.accessToken").exists(),
                    jsonPath("$.accessToken").isString(),
                    jsonPath("$.accessToken").value(not(oldToken)));
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
//            @Test
//            @DisplayName("refreshToken Cookie를 설정하지 않은 경우")
//            void withoutCookie() throws Exception {
//                // when
//                ResultActions result = authApi.refreshWithoutCookie();
//
//                // then
//                result.andExpectAll(
//                        status().isBadRequest(),
//                        jsonPath("$.message").exists());
//            }

            @Test
            @DisplayName("refresh가 아닌 다른 타입의 토큰을 사용한 경우")
            void useOtherTokenType() throws Exception {
                // given
                userApi.signup(aSignupRequestDto());
                var authResult = authApi.loginAndGetAuthResults(aLoginRequestDto());
                String accessToken = authResult.accessToken();

                // when
                ResultActions result = authApi.refresh(accessToken);

                // then
                result.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").exists());
            }

            @ParameterizedTest
            @ValueSource(strings = {"wrong_refresh_token", "", "  "})
            @DisplayName("유효하지 않은 RefreshToken을 사용한 경우")
            void invalidRefreshToken(String source) throws Exception {
                // given
                String wrongToken = source;

                // when
                ResultActions result = authApi.refresh(wrongToken);
                // then
                result.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").exists());
            }

            @Test
            @DisplayName("이미 사용한 RefreshToken을 재사용한 경우")
            void reuseToken() throws Exception {
                // given
                userApi.signup(aSignupRequestDto());
                String refreshToken = authApi.loginAndGetAuthResults(aLoginRequestDto()).refreshToken();
                authApi.refresh(refreshToken);

                // when
                ResultActions result = authApi.refresh(refreshToken);

                // then
                result.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").exists());
            }
        }
    }

    @Nested
    @DisplayName("로그아웃 API")
    class Logout {
        @Test
        @DisplayName("요청이 성공하면 토큰이 블랙리스트에 등록된다")
        void logout() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            var authResult = authApi.loginAndGetAuthResults(aLoginRequestDto());
            long before = blackedTokenRepository.count();

            // when
            ResultActions result = authApi.logout(authResult.accessToken(), authResult.refreshToken());

            // then
            result.andExpectAll(
                    status().isNoContent());
            long after = blackedTokenRepository.count();
            Assertions.assertThat(after - before).isEqualTo(2);
        }

        @Test
        @DisplayName("요청 이후 이전 엑세스 토큰을 사용할 수 없다")
        void usePreviousAccessToken() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            var authResult = authApi.loginAndGetAuthResults(aLoginRequestDto());
            authApi.logout(authResult.accessToken(), authResult.refreshToken());

            // when
            ResultActions result = authApi.autnenticate(authResult.accessToken());

            // then
            result.andExpectAll(
                    status().isUnauthorized());
        }

        @Test
        @DisplayName("요청 이후 이전 리프래시 토큰을 사용할 수 없다")
        void usePreviousRefreshToken() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            var authResult = authApi.loginAndGetAuthResults(aLoginRequestDto());
            authApi.logout(authResult.accessToken(), authResult.refreshToken());

            // when
            ResultActions result = authApi.refresh(authResult.refreshToken());

            // then
            result.andExpectAll(
                    status().isUnauthorized());
        }
    }

    @Nested
    @DisplayName("인증 테스트")
    class AuthenticationTest {
        @Test
        @DisplayName("인증 성공")
        void authenticationSuccess() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            authApi.login(aLoginRequestDto());
            String accessToken = authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken();

            // when
            ResultActions result = authApi.autnenticate(accessToken);

            // then
            result.andExpect(status().isOk());
        }

        @Test
        @DisplayName("인증 실패")
        void authenticationFailure() throws Exception {
            // given
            String token = "wrong_token";

            // when
            ResultActions result = authApi.autnenticate(token);

            // then
            result.andExpectAll(
                    status().isUnauthorized(),
                    jsonPath("$.message").exists()
            );
        }
    }

    @Nested
    @DisplayName("인가 테스트")
    class AuthorizationTest {
        @Test
        @DisplayName("인가 성공")
        void authorizationSuccess() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            User user = userRepository.findByEmail(DEFAULT_EMAIL).get();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            String token = authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken();

            // when
            ResultActions result = authApi.authorize(token);

            // then
            result.andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("인가 실패")
        void authorizationFailure() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            String token = authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken();

            // when
            ResultActions result = authApi.authorize(token);

            // then
            result.andExpectAll(
                    status().isForbidden(),
                    jsonPath("$.message").exists()
            );
        }
    }
}