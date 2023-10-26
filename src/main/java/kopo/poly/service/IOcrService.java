package kopo.poly.service;

import kopo.poly.dto.OcrDTO;

import java.util.List;

public interface IOcrService {

    /* 이미지 파일로부터 문자 읽어 오기 */
    OcrDTO getReadForImageText(OcrDTO pDTO) throws Exception;

    /* 저장하기 */
    void insertOcr(OcrDTO pDTO) throws Exception;

    /* 출력하기 */
    List<OcrDTO> getOcrList() throws Exception;

    /* 상세보기 */
    OcrDTO getOcrInfo(OcrDTO pDTO) throws Exception;
}
