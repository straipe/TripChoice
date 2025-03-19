package com.ssafy12.tripchoice.common.config;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GPTConfig {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

}
