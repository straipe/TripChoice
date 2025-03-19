package com.ssafy12.tripchoice.weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;

import com.ssafy12.tripchoice.weather.dto.WeatherResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    private static WeatherResponseDto.WeatherResponseDtoBuilder dtoBuilder;

    public WeatherResponseDto getNowWeather(int X, int Y, String Date, int page){
        String apikey = System.getenv("WEATHER_API_KEY");
        log.info("apikey = {}", apikey);
        String pattern = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey={0}&numOfRows=12&pageNo={1}&base_date={2}&base_time=0500&nx={3}&ny={4}";
        String url = MessageFormat.format(pattern, apikey, page, Date, X, Y);
        log.info("url = {}", url);
        dtoBuilder = WeatherResponseDto.builder();
        try{
            // 1. HTTP 요청 보내기
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url)) // 요청 보낼 URL
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("response = {}", response.body());

            // 2. XML 데이터 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            InputStream inputStream = new ByteArrayInputStream(response.body().getBytes("UTF-8"));
            Document document = builder.parse(inputStream);

            NodeList itemNodes = document.getElementsByTagName("item");

            for (int i = 0; i < itemNodes.getLength(); i++) {
                Node itemNode = itemNodes.item(i);
                if (itemNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element itemElement = (Element) itemNode;
                    String category = itemElement.getElementsByTagName("category").item(0).getTextContent();
                    String fcstValue = itemElement.getElementsByTagName("fcstValue").item(0).getTextContent();
                    switch(category){
                        case "POP":
                            dtoBuilder.pop(Integer.parseInt(fcstValue));
                            break;
                        case "PTY":
                            dtoBuilder.pty(Integer.parseInt(fcstValue));
                            break;
                        case "SKY":
                            dtoBuilder.sky(Integer.parseInt(fcstValue));
                            break;
                        case "TMP":
                            dtoBuilder.tmp(Integer.parseInt(fcstValue));
                            break;
                        case "PCP":
                            dtoBuilder.pcp(fcstValue);
                            break;
                        case "SNO":
                            dtoBuilder.sno(fcstValue);
                            break;
                        case "REH":
                            dtoBuilder.reh(Integer.parseInt(fcstValue));
                            break;
                        case "WSD":
                            dtoBuilder.wsd(Float.parseFloat(fcstValue));
                            break;
                    }
                }
            }

        } catch(Exception e){
            throw new RuntimeException(e);
        }

        return dtoBuilder.build();
    }

    public List<WeatherResponseDto> getWeatherList(int X, int Y, String Date){
        List<WeatherResponseDto> dtoList = new ArrayList<>();
        for(int p=1; p<=21;p+=4) {
            dtoList.add(getNowWeather(X, Y, Date, p));
        }

        return dtoList;
    }
}
