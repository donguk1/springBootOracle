package kopo.poly.persistance.mapper;

import kopo.poly.dto.MovieDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMovieMapper {


    /* 수집된 영화 순위 등록 */
    int insertMovieInfo(MovieDTO pDTO) throws Exception;

    /* 삭제 */
    void deleteMovieInfo(MovieDTO pDTO) throws Exception;

    /* 내용 조회 */
    List<MovieDTO> getMovieInfo(MovieDTO pDTO) throws Exception;
}
