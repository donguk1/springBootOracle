package kopo.poly.controller;

import kopo.poly.dto.WeatherDTO;
import kopo.poly.service.IWeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
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
}
