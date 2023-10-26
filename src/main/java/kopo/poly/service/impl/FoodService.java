package kopo.poly.service.impl;

import kopo.poly.dto.FoodDTO;
import kopo.poly.service.IFoodService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FoodService implements IFoodService {

    /**
     * 첫번째 : 초(0-59) <p>
     * 두번째 : 분(0-59)<p>
     * 세번째 : 시간(0-23)<p>
     * 네번째 : 일(1-31)<p>
     * 다섯번째 : 월(1-12)<p>
     * 여섯번째 : 요일(1-7, 1: 일, 7: 토)
     */
    @Scheduled(cron = "* * 3 * * *")    // 새벽 3시마다 실행
    @Override
    public List<FoodDTO> toDayFood() throws Exception {

        log.info(".service 폴리 식단 실행");

        int res = 0;

        String url = "https://www.kopo.ac.kr/kangseo/content.do?menu=262";

        Document doc = null;

        doc = Jsoup.connect(url).get();

        Elements element = doc.select("table.tbl_table tbody");

        Iterator<Element> foodIt = element.select("tr").iterator();

        FoodDTO pDTO = null;

        List<FoodDTO> pList = new ArrayList<>();
        int idx = 0;

        while (foodIt.hasNext()) {

            if (idx++ > 4) {
                break;
            }

            String food = CmmUtil.nvl(foodIt.next().text()).trim();

            log.info("food : " + food);

            pDTO = new FoodDTO();
            pDTO.setDay(food.substring(0,3));
            pDTO.setFood_nm(food.substring(4));

            pList.add(pDTO);
        }

        log.info(".service 폴리 식단 종료");

        return pList;
    }
}
