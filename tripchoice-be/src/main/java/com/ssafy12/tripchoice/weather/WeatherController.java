package com.ssafy12.tripchoice.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.ssafy12.tripchoice.common.config.GPTConfig;
import com.ssafy12.tripchoice.tag.dto.request.TagRequestDto;
import com.ssafy12.tripchoice.tag.dto.response.TagResponseDto;
import com.ssafy12.tripchoice.weather.dto.WeatherRequestDto;
import com.ssafy12.tripchoice.weather.dto.WeatherResponseDto;
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
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/now")
    public ResponseEntity<WeatherResponseDto> getNowWeather(@Valid WeatherRequestDto request){
        log.info("NowWeather = {}",request);
        int X = request.X();
        int Y = request.Y();

        try{
            LocalDate today = LocalDate.now();

            LocalTime now = LocalTime.now();
            int hour = now.getHour();
            if(hour > 5){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = today.format(formatter);

                return ResponseEntity.ok()
                        .body(weatherService.getNowWeather(X, Y, formattedDate, hour-5));
            } else {
                LocalDate yesterday = today.minusDays(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedDate = yesterday.format(formatter);

                return ResponseEntity.ok()
                        .body(weatherService.getNowWeather(X, Y, formattedDate, hour-5+24));
            }

        } catch(Exception e) {
            return ResponseEntity.badRequest()
                    .body(null);
        }

    }

    @GetMapping("/list")
    public ResponseEntity<List<WeatherResponseDto>> getWeatherList(@Valid WeatherRequestDto request){
        log.info("WeatherList = {}",request);
        int X = request.X();
        int Y = request.Y();
        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);

        return ResponseEntity.ok()
                .body(weatherService.getWeatherList(X, Y, formattedDate));
    }
}
