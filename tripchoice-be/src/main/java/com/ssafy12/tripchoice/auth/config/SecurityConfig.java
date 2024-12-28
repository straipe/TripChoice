package com.ssafy12.tripchoice.auth.config;

import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.auth.security.exceptionhandler.CustomAccessDeniedHandler;
import com.ssafy12.tripchoice.auth.security.exceptionhandler.CustomAuthenticationEntryPoint;
import com.ssafy12.tripchoice.auth.security.EmailPasswordAuthenticationProvider;
import com.ssafy12.tripchoice.auth.security.filter.JwtAuthenticationFilter;
import com.ssafy12.tripchoice.auth.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthProps authProps;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final EmailPasswordAuthenticationProvider emailPasswordAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(GET, "/api/auth/authentication").authenticated() // 인증 테스트 api
                        .requestMatchers(GET, "/api/auth/authorization").hasAuthority(UserRole.ADMIN.role()) // 인가 테스트 api
                        .requestMatchers(POST, "/api/users/signup").permitAll()
                        .requestMatchers(POST, "/api/auth/login").permitAll()
                        .requestMatchers(POST, "/api/auth/refresh").permitAll()
                        .requestMatchers(POST, "/api/boards/**").hasAuthority(UserRole.ADMIN.role())
                        .requestMatchers(PUT, "/api/plans/**").permitAll()
                        .requestMatchers(GET,"/api/users/me").authenticated()
                        .requestMatchers(PATCH,"/api/users/me").authenticated()
                        .requestMatchers(GET,"/api/**").permitAll() // 기본적으로 조회 요청은 모두 허
                        .requestMatchers(OPTIONS,"/api/**").permitAll()
                        .requestMatchers(GET, "/swagger-ui/**").permitAll()
                        .requestMatchers(GET,"v3/api-docs/**").permitAll()
                        .requestMatchers("/resources/**").denyAll() // 정적 리소스 요청은 모두 거부
                        .anyRequest().authenticated())
                .exceptionHandling(eh -> eh
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build(); //todo oauth 적용
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(emailPasswordAuthenticationProvider, jwtAuthenticationProvider);
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(authProps, authenticationManager());
    }
}
