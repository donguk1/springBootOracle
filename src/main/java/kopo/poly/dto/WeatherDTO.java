package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class WeatherDTO {

    private String currunt;


    private String lat; // 위도
    private String lon; // 경도
    private double currentTemp; // 현재 기온
    private List<WeatherDailyDTO> dailyList;
}
