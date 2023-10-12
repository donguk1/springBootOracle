package kopo.poly.controller;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/food")
public class FoodController {

    private final IFoodService foodService;

    @GetMapping(value = "todayFood")
    public String collectFood(ModelMap modelMap) throws Exception {

        log.info(".controller 음식 실행");

        List<FoodDTO> rList = Optional.ofNullable(foodService.toDayFood()).orElseGet(ArrayList::new);

        modelMap.addAttribute("rList", rList);

        log.info(".controller 음식 종료");

        return "/food/todayFood";

    }
}
