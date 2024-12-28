package com.ssafy12.tripchoice.user;

import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.user.dto.SignupRequestDto;
import com.ssafy12.tripchoice.user.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.ResultActions;

import static com.ssafy12.tripchoice.common.AccessTokenHolder.setAccessToken;
import static com.ssafy12.tripchoice.fixture.UserFixture.aLoginRequestDto;
import static com.ssafy12.tripchoice.fixture.UserFixture.aSignupRequestDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("회원가입 API")
    class SignUp {

        @Test
        @DisplayName("요청이 성공하면 회원이 생성된다")
        void success() throws Exception {
            // given
            var request = aSignupRequestDto();

            // when
            ResultActions result = userApi.signup(request);

            // then
            result.andExpect(status().isCreated());
            assertThat(userRepository.findByEmail(request.email()).isPresent()).isTrue();
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("이메일이 중복된 경우")
            void withEmailConflict() throws Exception {
                // given
                var request = aSignupRequestDto();
                userApi.signup(request);

                // when
                ResultActions result = userApi.signup(request);

                // then
                result.andExpectAll(
                        status().isConflict(),
                        jsonPath("$.message").exists());
            }

            @Nested
            @DisplayName("요청 데이터 검증과정에서")
            class WithValidation {

                @ParameterizedTest
                @NullSource
                @ValueSource(strings = {"", "  ", "mail.com", "test", "test@mail"})
                @DisplayName("이메일 양식이 올바르지 않은 경우")
                void wrongEmail(String email) throws Exception {
                    // given
                    var request = SignupRequestDto.builder()
                            .email(email)
                            .build();

                    // when
                    ResultActions result = userApi.signup(request);

                    // then
                    result.andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.message").exists());
                }

                @ParameterizedTest
                @NullSource
                @ValueSource(strings = {"", "  "})
                @DisplayName("비밀번호 양식이 올바르지 않은 경우")
                void wrongPassword(String password) throws Exception {
                    // given
                    var request = SignupRequestDto.builder()
                            .password(password)
                            .build();

                    // when
                    ResultActions result = userApi.signup(request);

                    // then
                    result.andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.message").exists());
                }

                @ParameterizedTest
                @NullSource
                @ValueSource(strings = {"", "  "})
                @DisplayName("사용자 이름 양식이 올바르지 않은 경우")
                void wrongName(String name) throws Exception {
                    // given
                    var request = SignupRequestDto.builder()
                            .name(name)
                            .build();

                    // when
                    ResultActions result = userApi.signup(request);

                    // then
                    result.andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.message").exists());
                }
            }
        }
    }

    @Nested
    @DisplayName("유저 정보 조회 API")
    class GetUser {
        @Test
        @DisplayName("요청이 성공한다")
        void success() throws Exception {
            // given
            User user = signUpAndLoginDefaultUser();


            // when
            ResultActions result = userApi.getUser();

            // then
            result.andExpectAll(
                    status().isOk(),
                    jsonPath("$.id").value(user.getId()),
                    jsonPath("$.email").value(user.getEmail()),
                    jsonPath("$.name").value(user.getName()));
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("인증이 실패한 경우")
            void notFound() throws Exception {
                // given
                User user = signUpAndLoginDefaultUser();
                AccessTokenHolder.clear();

                // when
                ResultActions result = userApi.getUser();

                // then
                result.andExpectAll(
                        status().isUnauthorized(),
                        jsonPath("$.message").exists());
            }
        }
    }

    @Nested
    @DisplayName("유저 정보 업데이트 API")
    class UpdateUser {
        @Test
        @DisplayName("요청이 성공한다")
        void success() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
            User user = userRepository.findAll().get(0);
            UserUpdateRequestDto request = UserUpdateRequestDto.builder()
                    .name("updateName")
                    .build();

            // when
            ResultActions result = userApi.update(request);

            // then
            result.andExpect(status().isNoContent());
            User updated = userRepository.findById(user.getId()).get();
            assertThat(updated.getName()).isEqualTo("updateName");
            assertThat(updated.getPassword()).isEqualTo(user.getPassword());
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {

            @Test
            @DisplayName("인증이 실패한 경우")
            void withoutAuthentication() throws Exception {
                // given
                userApi.signup(aSignupRequestDto());
                setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
                UserUpdateRequestDto request = UserUpdateRequestDto.builder()
                        .name("updateName")
                        .build();
                AccessTokenHolder.clear();

                // when
                ResultActions result = userApi.update(request);

                // then
                result.andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    @DisplayName("회원 탈퇴 API")
    class SignOut {
        @Test
        @DisplayName("요청이 성공한다")
        void success() throws Exception {
            // given
            userApi.signup(aSignupRequestDto());
            setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
            User user = userRepository.findAll().get(0);

            // when
            ResultActions result = userApi.signout();

            // then
            result.andExpect(status().isNoContent());
            assertThat(userRepository.existsById(user.getId())).isFalse();
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {

            @Test
            @DisplayName("인증이 실패한 경우")
            void withoutAuthentication() throws Exception {
                // given
                userApi.signup(aSignupRequestDto());
                setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
                AccessTokenHolder.clear();

                // when
                ResultActions result = userApi.signout();

                // then
                result.andExpect(status().isUnauthorized());
            }
        }
    }
}