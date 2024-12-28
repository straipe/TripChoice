package com.ssafy12.tripchoice.auth;

import com.google.common.net.HttpHeaders;
import com.ssafy12.tripchoice.auth.dto.AuthTokens;
import com.ssafy12.tripchoice.auth.dto.Login;
import com.ssafy12.tripchoice.auth.dto.Logout;
import com.ssafy12.tripchoice.auth.dto.request.AuthRequestDto;
import com.ssafy12.tripchoice.auth.dto.request.LoginRequestDto;
import com.ssafy12.tripchoice.auth.dto.response.AuthResponse;
import com.ssafy12.tripchoice.auth.jwt.SignedJwt;
import com.ssafy12.tripchoice.auth.resolver.AccessToken;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequestDto request) {
        log.debug("login request = {}", request);
        AuthTokens authTokens = authService.login(Login.builder()
                .email(request.email())
                .password(request.password())
                .build());
        log.debug("login success for = {}", request);
        return generateAuthResponse(authTokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody AuthRequestDto request) {
        log.debug("refresh request = {}", request.refreshToken());
        AuthTokens authTokens = authService.refresh(request.refreshToken());
        return generateAuthResponse(authTokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AccessToken @NotNull String accessToken,
                                       @RequestBody AuthRequestDto request) {
        log.info("logout request: accessToken = {}", accessToken);
        log.info("logout request: refreshToken = {}", request.refreshToken());
        authService.logout(Logout.builder()
                .accessToken(accessToken)
                .refreshToken(request.refreshToken())
                .build());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authentication")
    public ResponseEntity<String> authentication() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("인증 테스트 성공");
    }

    @GetMapping("/authorization")
    public ResponseEntity<String> authorization() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("인가 테스트 성공");
    }

    private static ResponseEntity<AuthResponse> generateAuthResponse(AuthTokens authTokens) {
//        String refreshCookie = generateRefreshCookie(authTokens.forRefresh());
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.SET_COOKIE, refreshCookie)
                .body(AuthResponse.builder()
                        .accessToken(authTokens.forAccess().value())
                        .refreshToken(authTokens.forRefresh().value())
                        .build());
    }

//    private static String generateRefreshCookie(SignedJwt refreshToken) {
//        return ResponseCookie
//                .from("refreshToken", refreshToken.value())
//                .domain(null) // 도메인 생략
//                .path("/") // 최상위 경로
//                .httpOnly(true)
//                .secure(true) // HTTPS 연결에서만 쿠키를 전송
//                .maxAge(refreshToken.expirySeconds())
//                .sameSite("None") // 크로스 오리진 요청 허용
//                .build().toString();
//    }
}
