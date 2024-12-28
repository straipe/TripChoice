package com.ssafy12.tripchoice.attraction;

import com.ssafy12.tripchoice.attraction.domain.*;
import com.ssafy12.tripchoice.attraction.dto.AttractionSearchDto;
import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.board.Board;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.ssafy12.tripchoice.fixture.BoardFixture.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class AttractionApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("관광지 페이징 API")
    class GetPage {

        @Test
        @DisplayName("요청이 성공하면 관광지 페이지가 조회된다")
        void success() throws Exception {
            // given

            // when
            ResultActions result = attractionApi.getPage(AttractionSearchDto.builder()
                    .page(1)
                    .size(10)
                    .key("key")
                    .build());

            // then
            result.andDo(print())
                    .andExpectAll(
                            status().isOk(),
                            jsonPath("$.currentPage").value(1),
                            jsonPath("$.size").value(10),
                            jsonPath("$.totalPage").isNumber(),
                            jsonPath("$.content").isArray(),
                            jsonPath("$.content", hasSize(10))
                    );
        }
    }
}