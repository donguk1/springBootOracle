package kopo.poly.service;

import kopo.poly.dto.MovieDTO;

import java.util.List;

public interface IMovieService {

    /* 영화 순위 가져오기 */
    int collectMovieRank() throws Exception;

    /* 내용 조회 */
    List<MovieDTO> getMovieInfo() throws Exception;
}
