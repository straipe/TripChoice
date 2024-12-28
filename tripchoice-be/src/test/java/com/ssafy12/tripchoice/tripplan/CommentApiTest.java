package com.ssafy12.tripchoice.tripplan;

import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.tripplan.comment.Comment;
import com.ssafy12.tripchoice.tripplan.comment.dto.request.CommentCreateRequestDto;
import com.ssafy12.tripchoice.tripplan.plan.Plan;
import com.ssafy12.tripchoice.tripplan.plan.dto.ScheduleCreateDto;
import com.ssafy12.tripchoice.tripplan.schedule.Schedule;
import com.ssafy12.tripchoice.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Comparator;
import java.util.List;

import static com.ssafy12.tripchoice.fixture.PlanFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class CommentApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("여행 계획 댓글 생성 API")
    class CreatePlanComment {

        @Test
        @DisplayName("요청이 성공하면 여행 계획에 댓글이 생성된다")
        void success() throws Exception {
            // given
            User user = signUpAndLoginDefaultUser();
            planApi.create(aPlanCreateRequestDto());
            Plan plan = planRepository.findAll().get(0);
            var request = CommentCreateRequestDto.builder()
                    .content("댓글 내용")
                    .build();

            // when
            ResultActions result = commentApi.create(plan.getId(), request);

            // then
            result.andExpect(status().isCreated());
            Comment comment = commentRepository.findAll().get(0);
            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(commentRepository.count()).isEqualTo(1);
                softly.assertThat(comment.getContent()).isEqualTo(request.content());
                softly.assertThat(comment.getPlanId()).isEqualTo(plan.getId());
                softly.assertThat(comment.getUserId()).isEqualTo(user.getId());
            });
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("인증이 실패한 경우")
            void withoutAuthentication() throws Exception {
                // given
                signUpAndLoginDefaultUser();
                planApi.create(aPlanCreateRequestDto());
                Plan plan = planRepository.findAll().get(0);
                var request = CommentCreateRequestDto.builder()
                        .content("댓글 내용")
                        .build();
                AccessTokenHolder.clear();

                // when
                ResultActions result = commentApi.create(plan.getId(), request);

                // then
                result.andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    @DisplayName("여행 계획 댓글 삭제 API")
    class DeletePlan {

        @Test
        @DisplayName("요청이 성공하면 여행 계획에서 댓글이 삭제된다")
        void success() throws Exception {
            // given
            signUpAndLoginDefaultUser();
            planApi.create(aPlanCreateRequestDto());
            Plan plan = planRepository.findAll().get(0);
            commentApi.create(plan.getId(), CommentCreateRequestDto.builder().content("content").build());
            Comment comment = commentRepository.findAll().get(0);

            // when
            ResultActions result = commentApi.deleteComment(plan.getId(),comment.getId());

            // then
            result.andExpect(status().isNoContent());
            assertThat(commentRepository.findAll().isEmpty()).isTrue();
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("인증이 실패한 경우")
            void withoutAuthentication() throws Exception {
                // given
                signUpAndLoginDefaultUser();
                planApi.create(aPlanCreateRequestDto());
                Plan plan = planRepository.findAll().get(0);
                commentApi.create(plan.getId(), CommentCreateRequestDto.builder().content("content").build());
                Comment comment = commentRepository.findAll().get(0);
                AccessTokenHolder.clear();

                // when
                ResultActions result = commentApi.deleteComment(plan.getId(),comment.getId());

                // then
                result.andExpect(status().isUnauthorized());
                assertThat(planRepository.findAll().isEmpty()).isFalse();
            }

            @Test
            @DisplayName("계획이 존재하지 않는 경우")
            void withNotFoundPlan() throws Exception {
                // given
                signUpAndLoginDefaultUser();

                // when
                ResultActions result = commentApi.deleteComment(0L, 0L);

                // then
                result.andExpect(status().isNotFound());
            }

            @Test
            @DisplayName("계획에 해당 댓글이 존재하지 않는 경우")
            void withNotFoundComment() throws Exception {
                // given
                signUpAndLoginDefaultUser();
                planApi.create(aPlanCreateRequestDto());
                Plan plan = planRepository.findAll().get(0);

                // when
                ResultActions result = commentApi.deleteComment(plan.getId(), 0L);

                // then
                result.andExpect(status().isNotFound());
            }
        }
    }
}