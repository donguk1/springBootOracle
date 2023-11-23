package kopo.poly.controller;

import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.OpenOption;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "papago")
public class PapagoController {

    private final IPapagoService papagoService;

    @PostMapping(value = "detectLangs")
    public PapagoDTO detectLangs(HttpServletRequest request) throws Exception {

        log.info("controller 언어 감지 실행");

        String text = CmmUtil.nvl(request.getParameter("text"));

        log.info("text : " + text);

        PapagoDTO pDTO = new PapagoDTO();
        pDTO.setText(text);

        PapagoDTO rDTO = Optional.ofNullable(papagoService.detectLangs(pDTO)).orElseGet(PapagoDTO::new);

        log.info("controller 언어 감지 종료");

        return rDTO;
    }

//    @GetMapping(value = "detect_lang")
//    public String detect_lang() {
//        return "/html/detect_lang";
//    }
}
