package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.WeatherDTO;
import kopo.poly.dto.WeatherDailyDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService implements IWeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Transactional
    @Override
    public WeatherDTO getCurrent() throws Exception {

        log.info(".service 날씨 가져오기");

        String url = "https://weather.naver.com/";

        Document doc = null;
        doc = Jsoup.connect(url).get();

        Elements elements = doc.select("div.weather_now");

        Iterator<Element> weather = elements.select("strong.current").iterator();

        if (weather.hasNext()) {
            String currentWeather = weather.next().text();
            log.info(currentWeather);

            WeatherDTO pDTO = new WeatherDTO();
            pDTO.setCurrunt(currentWeather);

            return pDTO;
        } else {
            return null;
        }

    }

    @Override
    public WeatherDTO getWeather(WeatherDTO pDTO) throws Exception {

        log.info("service 날씨 가져오기 실행");

        String lat = CmmUtil.nvl(pDTO.getLat());
        String lon = CmmUtil.nvl(pDTO.getLon());
        String apiParam = "?lat=" + lat
                + "&lon=" + lon
                + "&appid=" + apiKey + "&units=metric";
        String json = NetworkUtil.get(IWeatherService.apiURL + apiParam);

        log.info("lat : " + lat);
        log.info("lon : " + lon);
        log.info("apiParam : " + apiParam);
        log.info("json : " + json);

        // Json 구조를 Map 데이터 구조로 변경하기
        // 키와 값 구조의 JSON 구조로부터 데이터를 쉽게 가져오기 위해 MAP 데이터구로로 변경함
        // Object 결과 값의 데이터 타입이 다양하기에 사용
        Map<String, Object> rMap = new ObjectMapper().readValue(json, LinkedHashMap.class);
        // 현재 날씨 정보를 가지고 있는 current 키의 값 가져오기
        Map<String, Double> current = (Map<String, Double>) rMap.get("current");

        Double currentTemp = current.get("temp");   // 현재 기온

        log.info("현재 기온 : " + currentTemp);

        // 일별 날씨 조회(OpenAPI가 현재 날짜 기준으로 최대 7일까지 제공)
        // 7일 동안의 날씨 정보를 저장할 데이터
        List<Map<String, Object>> dailyList = (List<Map<String, Object>>) rMap.get("daily");
        // OpenAPI 로부터 필요한 정보만 가져와 처리하기 쉬운 JSON 구조로 변경하여 활용
        List<WeatherDailyDTO> pList = new LinkedList<>();

        for (Map<String, Object> dailyMap : dailyList) {
            String day = DateUtil.getLongDateTime(dailyMap.get("dt"), "yyyy-MM-dd");    // 기준 날짜
            String sunrise = DateUtil.getLongDateTime(dailyMap.get("sunrise"));
            String sunset = DateUtil.getLongDateTime(dailyMap.get("sunset"));
            String moonrise = DateUtil.getLongDateTime(dailyMap.get("moonrise"));
            String moonset = DateUtil.getLongDateTime(dailyMap.get("moonset"));

            log.info("-------------------------");
            log.info("today    : " + day);
            log.info("sunrise  : " + sunrise);
            log.info("sunset   : " + sunset);
            log.info("moonrise : " + moonrise);
            log.info("moonset  : " + moonset);

            Map<String, Double> dailyTemp = (Map<String, Double>) dailyMap.get("temp");

            Double dayTemp = dailyTemp.get("day");
            String dayTempMax = String.valueOf(dailyTemp.get("max"));
            String dayTempMin = String.valueOf(dailyTemp.get("min"));

            log.info("-------------------------");
            log.info("dayTemp    : " + dayTemp);
            log.info("dayTempMax : " + dayTempMax);
            log.info("dayTempMin : " + dayTempMin);

            // 이미지 가져오기
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) dailyMap.get("weather");

            Map<String, Object> weatherInfo = weatherList.get(0);
            String icon = (String) weatherInfo.get("icon");

            WeatherDailyDTO wdDTO = new WeatherDailyDTO();
            wdDTO.setDay(day);
            wdDTO.setSunrise(sunrise);
            wdDTO.setSunset(sunset);
            wdDTO.setMoonrise(moonrise);
            wdDTO.setMoonset(moonset);
            wdDTO.setDayTemp(String.valueOf((dayTemp)));
            wdDTO.setDayTempMax(dayTempMax);
            wdDTO.setDayTempMin(dayTempMin);
            wdDTO.setIcon(icon);

            pList.add(wdDTO);
            wdDTO = null;

        }

        WeatherDTO rDTO = new WeatherDTO();
        rDTO.setLat(lat);
        rDTO.setLon(lon);
        rDTO.setCurrentTemp(currentTemp);
        rDTO.setDailyList(pList);

        log.info("service 날씨 가져오기 종료");

        return rDTO;
    }
}
