package com.ssafy12.tripchoice.common;

import com.ssafy12.tripchoice.attraction.AttractionRepository;
import com.ssafy12.tripchoice.attraction.ContentTypeRepository;
import com.ssafy12.tripchoice.auth.blacklist.BlackedTokenRepository;
import com.ssafy12.tripchoice.board.BoardRepository;
import com.ssafy12.tripchoice.common.api.*;
import com.ssafy12.tripchoice.fixture.UserFixture;
import com.ssafy12.tripchoice.hotplace.HotPlaceRepository;
import com.ssafy12.tripchoice.tripplan.comment.CommentRepository;
import com.ssafy12.tripchoice.tripplan.plan.PlanRepository;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static com.ssafy12.tripchoice.common.AccessTokenHolder.setAccessToken;
import static com.ssafy12.tripchoice.fixture.UserFixture.aLoginRequestDto;
import static com.ssafy12.tripchoice.fixture.UserFixture.aSignupRequestDto;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class ApiTestSupport {

    @Autowired
    private CleanUp cleanUp;

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PlanApi planApi;

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected AuthApi authApi;

    @Autowired
    protected BlackedTokenRepository blackedTokenRepository;

    @Autowired
    protected BoardApi boardApi;

    @Autowired
    protected BoardRepository boardRepository;

    @Autowired
    protected CommentApi commentApi;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected AttractionApi attractionApi;

    @Autowired
    protected AttractionRepository attractionRepository;

    @Autowired
    protected ContentTypeRepository contentTypeRepository;

    @Autowired
    protected HotPlaceApi hotPlaceApi;

    @Autowired
    protected HotPlaceRepository hotPlaceRepository;

    @BeforeEach
    void beforeEach() {
        AccessTokenHolder.clear();
        cleanUp.all();
    }

    protected User signUpAndLoginDefaultUser() throws Exception {
        userApi.signup(aSignupRequestDto());
        setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
        return userRepository.findByEmail(UserFixture.DEFAULT_EMAIL).get();
    }

    protected User signUpDefaultUser() throws Exception {
        userApi.signup(aSignupRequestDto());
        return userRepository.findByEmail(UserFixture.DEFAULT_EMAIL).get();
    }

    protected void loginDefaultUser() throws Exception {
        setAccessToken(authApi.loginAndGetAuthResults(aLoginRequestDto()).accessToken());
    }
}
