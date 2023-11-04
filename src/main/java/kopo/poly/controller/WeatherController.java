package kopo.poly.controller;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.OpenOption;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/weather")
public class WeatherController {

    private final IWeatherService weatherService;

    @GetMapping(value = "weather")
    public String weather(Model model) throws Exception {

        log.info(".controller 날씨");

        WeatherDTO pDTO = weatherService.getCurrent();

        log.info("123 : " + pDTO);

        model.addAttribute("msg", pDTO.getCurrunt());

        return "/weather/weather";


    }

    @GetMapping(value = "getWeather")
    public WeatherDTO getWeather(HttpServletRequest request) throws Exception {

        log.info("controller 날씨 가져오기 실행");

        String lat = CmmUtil.nvl(request.getParameter("lat"));
        String lon = CmmUtil.nvl(request.getParameter("lon"));

        WeatherDTO pDTO = new WeatherDTO();
        pDTO.setLat(lat);
        pDTO.setLon(lon);

        WeatherDTO rDTO = Optional.ofNullable(weatherService.getWeather(pDTO)).orElseGet(WeatherDTO::new);

        log.info("controller 날씨 가져오기 종료");

        return rDTO;
    }
}
