package kopo.poly.persistance.mapper;

import kopo.poly.dto.OcrDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IOcrMapper {

    /* 저장하기 */
    void insertOcr(OcrDTO pDTO) throws Exception;

    /* 출력하기 */
    List<OcrDTO> getOcrList() throws Exception;

    /* 상세보기 */
    OcrDTO getOcrInfo(OcrDTO pDTO) throws Exception;
}
