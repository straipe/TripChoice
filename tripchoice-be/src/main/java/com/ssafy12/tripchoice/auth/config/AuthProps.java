package com.ssafy12.tripchoice.auth.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthProps {

    @NotBlank
    public String header;

    @NotBlank
    public String scheme;
}
