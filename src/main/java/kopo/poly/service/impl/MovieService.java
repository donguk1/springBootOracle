package kopo.poly.service.impl;

import kopo.poly.dto.MovieDTO;
import kopo.poly.persistance.mapper.IMovieMapper;
import kopo.poly.service.IMovieService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MovieService implements IMovieService {

    private final IMovieMapper movieMapper;

    /* 영화 순위 가져오기 */
    @Transactional
    @Override
    public int collectMovieRank() throws Exception {

        log.info(".service 영화 순위 등록 실행");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        movieMapper.deleteMovieInfo(pDTO);

        pDTO = null;

        int res = 0;

        // 순위 정보 가져올 사이트
        String url = "http://www.cgv.co.kr/movies/";

        // 사이트 접속시 전체 HTML 소스 저장할 변수
        Document doc = null;
        // 사이트 접속
        doc = Jsoup.connect(url).get();

        // 전체 소스중 일부 태그만 선택하기 위해 사용
        // <div class="sect-movie-chart"> 태그 내에 있는 소스만 저장
        Elements element = doc.select("div.sect-movie-chart");

        // 영화 순위 정보 가져오기
        Iterator<Element> movie_rank = element.select("strong.rank").iterator();
        Iterator<Element> movie_name = element.select("strong.title").iterator();
        Iterator<Element> movie_reserve = element.select("strong.percent span").iterator();
        Iterator<Element> score = element.select("span.percent").iterator();
        Iterator<Element> open_day = element.select("span.txt-info").iterator();

        // 정보 저장
        while (movie_rank.hasNext()) {

            // 홈페이지 개발자들이 공백을 사용할수 있어 trim() 함수 사용
            String rank = CmmUtil.nvl(movie_rank.next().text()).trim();

            pDTO = new MovieDTO();
            pDTO.setCollectTime(collectTime);   // PK
            pDTO.setMovieRank(rank.substring(3, rank.length()));
            pDTO.setMovieNm(CmmUtil.nvl(movie_name.next().text()).trim());
            pDTO.setMovieReserve(CmmUtil.nvl(movie_reserve.next().text()).trim());
            pDTO.setScore(CmmUtil.nvl(score.next().text()).trim());
            pDTO.setOpenDay(CmmUtil.nvl(open_day.next().text()).trim().substring(0, 10));
            pDTO.setRegId("admin");

            res += movieMapper.insertMovieInfo(pDTO);

        }

        log.info(".service 영화 순위 등록 종료");

        return res;
    }

    /* 내용 조회 */
    @Override
    public List<MovieDTO> getMovieInfo() throws Exception {

        log.info(".service 영화 내용 조회 실행");

        String collectTime = DateUtil.getDateTime("yyyyMMdd");

        MovieDTO pDTO = new MovieDTO();
        pDTO.setCollectTime(collectTime);

        List<MovieDTO> rList = movieMapper.getMovieInfo(pDTO);

        log.info(".service 영화 내용 조회 종료");

        return rList;
    }
}
