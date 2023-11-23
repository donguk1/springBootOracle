package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.PapagoDTO;
import kopo.poly.service.IPapagoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.NetworkUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PapagoService implements IPapagoService {

    @Value("${naver.papago.clientId}")
    private String clientId;

    @Value("${naver.papago.clientSecret}")
    private String clientSecret;


    /* 네이버 API 사용을 위한 접속정보 설정하기 */
    private Map<String, String> setNaverInfo() {

        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-Naver-Client-Id", clientId);
        requestHeader.put("X-Naver-Client-Secret", clientSecret);

        log.info("clientId : " + clientId);
        log.info("clientSecret : " + clientSecret);

        return requestHeader;
    }

    /* 언어 감지 */
    @Override
    public PapagoDTO detectLangs(PapagoDTO pDTO) throws Exception {

        log.info("service 파파고 언어감지 실행");

        String text = CmmUtil.nvl(pDTO.getText());
        String param = "query=" + URLEncoder.encode(text, "UTF-8");
        String json = NetworkUtil.post(IPapagoService.detectLangsApiURL, param, this.setNaverInfo());

        log.info("json : " + json);

        PapagoDTO rDTO = new ObjectMapper().readValue(json, PapagoDTO.class);
        rDTO.setText(text);

        log.info("service 언어감지 종료");

        return rDTO;
    }
}
