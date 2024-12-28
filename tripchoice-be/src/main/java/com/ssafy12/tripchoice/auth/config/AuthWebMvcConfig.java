package com.ssafy12.tripchoice.auth.config;

import com.ssafy12.tripchoice.auth.resolver.AccessTokenResolver;
import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserIdResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(List.of(
                new AccessTokenResolver(),
                new AuthenticatedUserIdResolver())
        );
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") //todo origin change
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
