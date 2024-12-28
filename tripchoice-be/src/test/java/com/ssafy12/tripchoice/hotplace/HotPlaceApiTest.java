package com.ssafy12.tripchoice.hotplace;

import com.ssafy12.tripchoice.attraction.domain.ContentType;
import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.board.Board;
import com.ssafy12.tripchoice.board.dto.request.BoardCreateRequestDto;
import com.ssafy12.tripchoice.board.dto.request.BoardUpdateRequestDto;
import com.ssafy12.tripchoice.common.AccessTokenHolder;
import com.ssafy12.tripchoice.common.ApiTestSupport;
import com.ssafy12.tripchoice.common.dto.PageRequestDto;
import com.ssafy12.tripchoice.hotplace.dto.request.HotPlaceCreateRequestDto;
import com.ssafy12.tripchoice.user.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import static com.ssafy12.tripchoice.fixture.BoardFixture.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
public class HotPlaceApiTest extends ApiTestSupport {

    @Nested
    @DisplayName("핫플레이스 생성 API")
    class CreatBoard {
        @Test
        @DisplayName("요청이 성공하면 핫플레이스가 생성된다")
        void success() throws Exception {
            // given
            ContentType contentType = contentTypeRepository.findAll().get(0);
            byte[] imageData = "sample image data".getBytes(); // 테스트용 이미지 데이터
            var request = HotPlaceCreateRequestDto.builder()
                    .title("Test Place")
                    .contentTypeId(contentType.getContentTypeId())
                    .address("123 Test Address")
                    .summary("Test Summary")
                    .longitude(123.45)
                    .latitude(67.89)
                    .image(imageData)
                    .build();
            signUpAndLoginDefaultUser();

            // when
            ResultActions result = hotPlaceApi.create(request);

            // then
            result.andExpect(status().isCreated());
            HotPlace hotPlace = hotPlaceRepository.findAll().get(0);
            assertEquals("Test Place", hotPlace.getTitle());
            assertArrayEquals(imageData, hotPlace.getImage());
        }
    }
}