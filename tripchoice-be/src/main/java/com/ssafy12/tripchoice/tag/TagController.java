package com.ssafy12.tripchoice.tag;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.ssafy12.tripchoice.common.config.GPTConfig;
import com.ssafy12.tripchoice.tag.dto.request.TagRequestDto;
import com.ssafy12.tripchoice.tag.dto.response.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private static final int MAX_TOKENS = 1024;
    private final GPTConfig gptConfig;

    @GetMapping
    public ResponseEntity<TagResponseDto> getTags(@Valid TagRequestDto request){
        System.out.println("request: " + request);
        try{
            String url = "https://api.openai.com/v1/chat/completions";
            String prompt = "주소가 "+request.address()+"인 한국 관광지 관련 태그 12개를 추천해줘.\n" +
                    "형식은 아래와 같아야 해:\n" +
                    "{\n" +
                    "  \"tags\": [\"#태그1\", \"#태그2\", \"#태그3\", ..., \"#태그12\"]\n" +
                    "}";
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", request.model());
            requestBody.put("max_tokens", MAX_TOKENS);
            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.put(message);
            requestBody.put("messages", messages);
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + gptConfig.getApiKey());

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            StringBuilder response = new StringBuilder();
            try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
            List<String> tags = parse(response.toString());

            return ResponseEntity.ok()
                    .body(TagResponseDto.builder()
                            .content(tags)
                            .build());
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.notFound()
                    .build();
        }
    }

    private static List<String> parse(String responseBody) throws JSONException {
        JSONObject jsonObject = new JSONObject(responseBody);
        JSONArray choices = jsonObject.getJSONArray("choices");
        String responseGPT = choices.getJSONObject(0).getJSONObject("message").getString("content");
        System.out.println("GPT의 응답: "+responseGPT);
        jsonObject = new JSONObject(responseGPT);
        JSONArray tagsArray = jsonObject.getJSONArray("tags");
        List<String> tagsList = new ArrayList<>();
        for (int i = 0; i < tagsArray.length(); i++) {
            tagsList.add(tagsArray.getString(i));
        }
        System.out.println("GPT의 태그 리스트: "+tagsList);
        return tagsList;
    }
}
