package com.ssafy12.tripchoice.board;

import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.ssafy12.tripchoice.fixture.BoardFixture.*;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class BoardApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("공지사항 생성 API")
    class CreatBoard {

        @Test
        @DisplayName("요청이 성공하면 공지사항이 생성된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            var request = BoardCreateRequestDto.builder()
                    .title("공지사항 제목")
                    .content("공지입니다")
                    .build();

            // when
            ResultActions result = boardApi.create(request);

            // then
            result.andExpect(status().isCreated());
            var sut = boardRepository.findAll().get(0);
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(boardRepository.count()).isEqualTo(1);
                softly.assertThat(sut.getTitle()).isEqualTo(request.title());
                softly.assertThat(sut.getContent()).isEqualTo(request.content());
                softly.assertThat(sut.getUserId()).isEqualTo(user.getId());
            });
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("관리자 권한이 없는 경우")
            void withoutAuthentication() throws Exception {
                // given
                signUpAndLoginDefaultUser(); // Role = USER
                var request = BoardCreateRequestDto.builder()
                        .title("공지사항 제목")
                        .content("공지입니다")
                        .build();

                // when
                ResultActions result = boardApi.create(request);

                // then
                result.andExpect(status().isForbidden());
            }
        }
    }

    @Nested
    @DisplayName("공지사항 수정 API")
    class UpdateBoard {

        @Test
        @DisplayName("요청이 성공하면 공지사항이 수정된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            boardApi.create(aBoardCreateRequestDto());
            Board board = boardRepository.findAll().get(0);

            var request = BoardUpdateRequestDto.builder()
                    .title("공지사항 수정")
                    .content("수정 공지입니다")
                    .build();

            // when
            ResultActions result = boardApi.updateBoard(board.getId(), request);

            // then
            result.andExpect(status().isNoContent());
            var sut = boardRepository.findAll().get(0);
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(boardRepository.count()).isEqualTo(1);
                softly.assertThat(sut.getTitle()).isEqualTo(request.title());
                softly.assertThat(sut.getContent()).isEqualTo(request.content());
                softly.assertThat(sut.getUserId()).isEqualTo(user.getId());
            });
        }
    }

    @Nested
    @DisplayName("공지사항 삭제 API")
    class DeleteBoard {

        @Test
        @DisplayName("요청이 성공하면 공지사항이 삭제된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            boardApi.create(aBoardCreateRequestDto());
            Board board = boardRepository.findAll().get(0);

            // when
            ResultActions result = boardApi.deleteBoard(board.getId());

            // then
            result.andExpect(status().isNoContent());
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(boardRepository.count()).isEqualTo(0);
            });
        }
    }


    @Nested
    @DisplayName("공지사항 조회 API")
    class GetBoard {

        @Test
        @DisplayName("요청이 성공하면 공지사항이 조회된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            boardApi.create(aBoardCreateRequestDto());
            AccessTokenHolder.clear();
            Board board = boardRepository.findAll().get(0);

            // when
            ResultActions result = boardApi.getBoard(board.getId());

            // then
            result.andExpectAll(
                    status().isOk(),
                    jsonPath("$.boardId").value(board.getId().toString()),
                    jsonPath("$.title").value(DEFAULT_TITLE),
                    jsonPath("$.content").value(DEFAULT_CONTENT),
                    jsonPath("$.writer").value(user.getName()),
                    jsonPath("$.createdAt").value(board.getCreatedAt().toString())
            );
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {

            @Test
            @DisplayName("공지사항이 존재하지 않는 경우")
            void withNotFound() throws Exception {
                // given
                Long boardId = 0L;

                // when
                ResultActions result = boardApi.getBoard(boardId);

                // then
                result.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("공지사항 페이징 API")
    class GetPage {

        @Test
        @DisplayName("요청이 성공하면 공지사항 페이지가 조회된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            for (int i = 0; i < 10; i++) {
                boardApi.create(aBoardCreateRequestDto());
            }

            // when
            ResultActions result = boardApi.getPage(PageRequestDto.builder()
                    .page(1)
                    .size(5)
                    .build());

            // then
            result.andDo(print())
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.currentPage").value(1),
                            jsonPath("$.size").value(5),
                            jsonPath("$.totalPage").value(2),
                            jsonPath("$.content").isArray(),
                            jsonPath("$.content", hasSize(5))
                    );
        }
    }
}