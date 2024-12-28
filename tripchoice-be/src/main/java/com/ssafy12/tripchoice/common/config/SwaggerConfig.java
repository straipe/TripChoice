package com.ssafy12.tripchoice.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "TripChoice API 명세서",
                description = "<h3>TripChoice API Reference for Developers</h3>Swagger를 이용한 TripChoice API<br>",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {
}