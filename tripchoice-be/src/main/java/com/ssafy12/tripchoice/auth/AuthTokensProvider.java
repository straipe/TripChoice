package com.ssafy12.tripchoice.auth;

import com.ssafy12.tripchoice.auth.dto.AuthTokens;
import com.ssafy12.tripchoice.auth.jwt.JwtProvider;
import com.ssafy12.tripchoice.auth.jwt.JwtType;
import com.ssafy12.tripchoice.auth.jwt.SignedJwt;
import com.ssafy12.tripchoice.auth.security.CustomAuthentication;
import com.ssafy12.tripchoice.auth.security.ul.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthTokensProvider {
    private final JwtProvider jwtProvider;

    public AuthTokens generate(Authentication authentication) {
        if (!authentication.isAuthenticated()) { // precondition
            throw new IllegalArgumentException("인증되지 않은 인증정보로는 토큰을 생성할 수 없습니다");
        }

        if (!(authentication instanceof CustomAuthentication)) { // precondition
            throw new IllegalArgumentException("지원하지 않는 형태의 인증 정보입니다");
        }

        UserId userId = ((CustomAuthentication) authentication).getPrincipal();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return AuthTokens.builder()
                .forAccess(generate(userId, roles, JwtType.ACCESS))
                .forRefresh(generate(userId, roles, JwtType.REFRESH))
                .build();
    }

    private SignedJwt generate(UserId userId, List<String> roles, JwtType type) {
        return jwtProvider.generate(userId.userId(), roles, type);
    }
}
