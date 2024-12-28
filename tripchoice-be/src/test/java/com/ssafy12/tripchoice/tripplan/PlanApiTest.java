package com.ssafy12.tripchoice.tripplan;

import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class PlanApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("여행 계획 생성 API")
    class CreatePlan {

        @Test
        @DisplayName("요청이 성공하면 여행 계획이 생성된다")
        void success() throws Exception {
            // given
            signUpAndLoginDefaultUser();
            var request = aPlanCreateRequestDtoWithNSchedules(3);
            long nBeforeRequest = planRepository.count();

            // when
            ResultActions result = planApi.create(request);

            // then
            result.andExpect(status().isCreated());
            Plan sut = planRepository.findAll().get(0);
            assertThat(planRepository.count()).isEqualTo(nBeforeRequest + 1);
            assertThat(sut.getSchedules().size()).isEqualTo(3);
        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {
            @Test
            @DisplayName("인증이 실패한 경우")
            void withoutAuthentication() throws Exception {
                // given
                signUpAndLoginDefaultUser();
                var request = aPlanCreateRequestDtoWithNSchedules(3);
                AccessTokenHolder.clear();

                // when
                ResultActions result = planApi.create(request);

                // then
                result.andExpect(status().isUnauthorized());
            }
        }
    }

    @Nested
    @DisplayName("여행 계획 수정 API")
    class UpdatePlan {

        @Test
        @DisplayName("요청이 성공하면 여행 계획이 변경된다")
        void success() throws Exception {
            // given
            User user = signUpAndLoginDefaultUser();
            planApi.create(aPlanCreateRequestDtoWithNSchedules(3));
            Plan plan = planRepository.findAll().get(0);
            List<ScheduleCreateDto> scheduleCreateDtos = List.of(aScheduleCreateRequestDto(), aScheduleCreateRequestDto());

            var request = aPlanUpdateRequestDtoBuilder()
                    .title("updateTitle")
                    .summary("updateSummary")
                    .scheduleCreateDtos(scheduleCreateDtos)
                    .build();

            // when
            ResultActions result = planApi.updatePlan(plan.getId(), request);

            // then
            result.andExpect(status().isNoContent());

            Plan updatedPlan = planRepository.findAll().get(0);

            SoftAssertions.assertSoftly(softly -> {
                softly.assertThat(updatedPlan.getTitle()).isEqualTo("updateTitle");
                softly.assertThat(updatedPlan.getSummary()).isEqualTo("updateSummary");
                softly.assertThat(updatedPlan.getSchedules())
                        .isNotEmpty()
                        .hasSize(2);
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
                planApi.create(aPlanCreateRequestDtoWithNSchedules(3));
                Plan plan = planRepository.findAll().get(0);
                List<ScheduleCreateDto> scheduleCreateDtos = List.of(aScheduleCreateRequestDto(), aScheduleCreateRequestDto());
                AccessTokenHolder.clear();
                var request = aPlanUpdateRequestDtoBuilder()
                        .title("updateTitle")
                        .summary("updateSummary")
                        .scheduleCreateDtos(scheduleCreateDtos)
                        .build();

                // when
                ResultActions result = planApi.updatePlan(plan.getId(), request);

                // then
                result.andExpect(status().isUnauthorized());
            }

            @Test
            @DisplayName("계획이 존재하지 않는 경우")
            void withNotFound() throws Exception {
                // given
                User user = signUpAndLoginDefaultUser();
                var request = aPlanUpdateRequestDtoBuilder().build();

                // when
                ResultActions result = planApi.updatePlan(0L, request);

                // then
                result.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("여행 계획 삭제 API")
    class DeletePlan {

        @Test
        @DisplayName("요청이 성공하면 여행 계획이 삭제된다")
        void success() throws Exception {
            // given
            signUpAndLoginDefaultUser();
            planApi.create(aPlanCreateRequestDto());
            Plan plan = planRepository.findAll().get(0);

            // when
            ResultActions result = planApi.deletePlan(plan.getId());

            // then
            result.andExpect(status().isNoContent());
            assertThat(planRepository.findAll().isEmpty()).isTrue();
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
                AccessTokenHolder.clear();

                // when
                ResultActions result = planApi.deletePlan(plan.getId());

                // then
                result.andExpect(status().isUnauthorized());
                assertThat(planRepository.findAll().isEmpty()).isFalse();
            }

            @Test
            @DisplayName("계획이 존재하지 않는 경우")
            void withNotFound() throws Exception {
                // given
                signUpAndLoginDefaultUser();

                // when
                ResultActions result = planApi.deletePlan(0L);

                // then
                result.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("여행 계획 조회 API")
    class GetPlan {
//
//        @Test
//        @DisplayName("요청이 성공하면 여행 계획이 조회된다")
//        void success() throws Exception {
//            // given
//            User user = signUpAndLoginDefaultUser();
//            planApi.create(aPlanCreateRequestDtoWithNSchedules(3));
//            Plan plan = planRepository.findAll().get(0);
//            List<String> scheduleIds = plan.getSchedules().stream()
//                    .sorted(Comparator.comparingInt(Schedule::getSequence))
//                    .map(s -> s.getId().toString())
//                    .toList();
//
//            // when
//            ResultActions result = planApi.getPlan(plan.getId());
//
//            // then
//            result.andExpectAll(
//                    status().isOk(),
//                    jsonPath("$.userId").value(user.getId().toString()),
//                    jsonPath("$.title").value(DEFAULT_TITLE),
//                    jsonPath("$.summary").value(DEFAULT_SUMMARY)
//            );
//        }

        @Nested
        @DisplayName("요청이 실패한다")
        class Failure {

            @Test
            @DisplayName("계획이 존재하지 않는 경우")
            void withNotFound() throws Exception {
                // given
                Long planId = 0L;

                // when
                ResultActions result = planApi.getPlan(planId);

                // then
                result.andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    @DisplayName("여행 계획 페이징 API")
    class GetPage {

        @Test
        @DisplayName("요청이 성공하면 페이지가 조회된다")
        void success() throws Exception {
            // given
            User user = signUpDefaultUser();
            user.changeRole(UserRole.ADMIN);
            userRepository.save(user);
            loginDefaultUser();
            for (int i = 0; i < 10; i++) {
                planApi.create(aPlanCreateRequestDto());
            }

            // when
            ResultActions result = planApi.getPage(PageRequestDto.builder()
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