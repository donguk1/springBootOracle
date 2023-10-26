package kopo.poly.service.impl;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService implements IWeatherService {

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
}
