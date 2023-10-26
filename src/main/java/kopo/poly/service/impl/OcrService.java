package kopo.poly.service.impl;

import kopo.poly.dto.OcrDTO;
import kopo.poly.persistance.mapper.IOcrMapper;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OcrService implements IOcrService {

    private final IOcrMapper ocrMapper;

    @Value(value = "${ocr.model.data}")
    private String ocrModel;


    /* 이미지 파일로부터 문자 읽어 오기 */
    @Override
    public OcrDTO getReadForImageText(OcrDTO pDTO) throws Exception {

        log.info(".service 이미지 파일 읽기 시작");

        File imageFile = new File(CmmUtil.nvl(pDTO.getFilePath()) + "//" + CmmUtil.nvl(pDTO.getFileName()));

        // OCR 기술 사용을 위한 Tesseract 플랫폼 객체 생성
        ITesseract instance = new Tesseract();

        // OCR 분석에 필요한 기준 데이터(이미 각 나라의 언어별로 학습시킨 데이터 위치 폴더)
        // 저장 경로는 물리경로를 사용함(전체 경로)
        instance.setDatapath(ocrModel);

        // 한국어 학습 데이터 선택(기본 값은 영어)
        instance.setLanguage("kor");

        // 이미지 파일로부터 텍스트 읽기
        String result = instance.doOCR(imageFile);

        // 읽은 글자를 DTO에 저장하기
        pDTO.setTextFromImage(result);

        log.info("result : " + result);
        log.info(".service 이미지 파일 읽기 종료");

        return pDTO;

    }

    /* 저장하기 */
    @Override
    public void insertOcr(OcrDTO pDTO) throws Exception {

        log.info(".service ocrText 저장하기 실행");

        ocrMapper.insertOcr(pDTO);

    }

    /* 출력하기 */
    @Override
    public List<OcrDTO> getOcrList() throws Exception {

        log.info(".service OcrList 가져오기");

        return ocrMapper.getOcrList();
    }

    /* 상세보기 */
    @Override
    public OcrDTO getOcrInfo(OcrDTO pDTO) throws Exception {

        log.info(".service ocr 상세보기 실행");

        return ocrMapper.getOcrInfo(pDTO);
    }
}
