package kopo.poly.controller;

import kopo.poly.dto.MovieDTO;
import kopo.poly.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/movie")
@Controller
public class MovieController {

    private final IMovieService movieService;

    /* 영화 순위 가져오기 */
    @GetMapping (value = "collectMovieRank")
    public String collectMovieRank(ModelMap modelMap) throws Exception {

        log.info(".controller 영화 순위 가져오기 실행");

        int res = movieService.collectMovieRank();

        modelMap.addAttribute("msg", "CGV 홈페이지로부터 수집된 영화는 총 " + res + "건입니다.");

        log.info(".controller 영화 순위 가져오기 종료");

        return "/movie/collectMovieRank";
    }

    /* 내용 조회 */
    @GetMapping(value = "movieList")
    public String getMovieRank(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(".controller 영화 내용 조회 실행");

        List<MovieDTO> rList = Optional.ofNullable(movieService.getMovieInfo()).orElseGet(ArrayList::new);

        modelMap.addAttribute("rList", rList);

        log.info(".controller 영화 내용 조회 종료");

        return "/movie/movieList";
    }
}
