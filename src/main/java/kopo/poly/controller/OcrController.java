package kopo.poly.controller;

import kopo.poly.dto.OcrDTO;
import kopo.poly.service.IOcrService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.spec.OAEPParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping(value = "/ocr")
public class OcrController {

    private final IOcrService ocrService;

    final private String FILE_UPLOAD_SAVE_PATH = "c:/upload";



    /* 이미지 인식을 위한 파일업로드 화면 호출 */
    @GetMapping(value = "uploadImage")
    public String uploadImage() {

        log.info(".controller 업로드이미지로 이동");

        return "/ocr/uploadImage";
    }

    /* 파일 업로드 및 이미지 인식 */
    @PostMapping(value = "readImage")
    public String readImage(Model model,
                            @RequestParam(value = "fileUpload")MultipartFile mf) throws Exception {

        log.info(".controller 이미지 읽기 시작");

        String res = "";

        // 실제 업로드하는 파일명
        // 다운로드 기능 구현시, 임의로 정의된 파일명을 원래대로 만들어주기 위한 목적
        String originalFileName = mf.getOriginalFilename();
        String  size = String.valueOf(mf.getSize());

        // 파일 확장자 가져오기(파일 확장자를 포함한 전체 이름(myImage.jpg) 에서 뒤쪽부터 .이 존재하는 위치 찾기
        String ext = originalFileName.substring(originalFileName.lastIndexOf(".") + 1,
                originalFileName.length()).toLowerCase();

        // 이미지 파일만 실행되도록 함
        if (ext.equals("jpeg") || ext.equals("jpg") || ext.equals("gif") || ext.equals("png")) {

            // 웹서버에 저장되는 파일명
            // 업로드하는 파일 이름에 한글, 특수 문자들이 저장될 수 있기 때문에 강제로 영어와 숫자로 구성된 파일명으로 변경 저장
            // 리눅스나 유닉스 등 운영체제는 다국어 지원에 취약하기에
            String saveFileName = DateUtil.getDateTime("HHmmss") + "." + ext;

            // 웹서버엥 업로드한 파일 저장하는 물리적 경로
            String saveFilePath = FileUtil.mkdirForData(FILE_UPLOAD_SAVE_PATH);

            String fullFileInfo = saveFilePath + "/" + saveFileName;

            log.info("ext : " + ext);
            log.info("saveFileName : " + saveFileName);
            log.info("saveFilePath : " + saveFilePath);
            log.info("fullFileInfo : " + fullFileInfo);
            log.info("size : " + size);

            // 업로드 되는 파일을 서버에 저장
            mf.transferTo(new File(fullFileInfo));

            OcrDTO pDTO = new OcrDTO();
            pDTO.setFileName(saveFileName);
            pDTO.setFilePath(saveFilePath);
            pDTO.setExt(ext);
            pDTO.setOrgFileName(originalFileName);
            pDTO.setRegId("admin");
            pDTO.setId("admin");
            pDTO.setFileSize(size);

            OcrDTO rDTO = Optional.ofNullable(ocrService.getReadForImageText(pDTO)).orElseGet(OcrDTO::new);

            res = CmmUtil.nvl(rDTO.getTextFromImage());

            pDTO.setOcrText(res);

            ocrService.insertOcr(pDTO);

            rDTO = null;
            pDTO = null;

        } else {
            res = "이미지 파일이 아니라서 인식이 불가능합니다.";

        }

        model.addAttribute("res", res);

        log.info(".controller 이미지 읽기 종료");

        return "ocr/readImage";
    }

    /* 리스트 출력페이지 */
    @GetMapping(value = "ocrList")
    public String getOcrList(Model model) throws Exception {

        log.info(".controller ocrList.jsp 출력 실행");

        List<OcrDTO> rList = Optional.ofNullable(ocrService.getOcrList()).orElseGet(ArrayList::new);

        model.addAttribute("rList", rList);

        return "/ocr/ocrList";

    }

    /* Ocr 내용 페이지 이동 */
    @GetMapping(value = "ocrInfo")
    public String ocrInfo(HttpServletRequest request, Model model) throws Exception {

        log.info(".controller ocrInfo 이동");

        String seq = CmmUtil.nvl(request.getParameter("seq"));

        log.info("seq : " + seq);

        OcrDTO pDTO = new OcrDTO();
        pDTO.setSeq(seq);

        OcrDTO rDTO = Optional.ofNullable(ocrService.getOcrInfo(pDTO)).orElseGet(OcrDTO::new);

        model.addAttribute("rDTO", rDTO);

        return "/ocr/ocrInfo";

    }

    /* 다운로드 */
    @GetMapping(value = "download")
    public void download(HttpServletResponse response, HttpServletRequest request,
                         @RequestParam("seq") String seq) throws Exception {

        log.info(".controller 설치 실행");

        log.info("seq : " + seq);

        try {
            OcrDTO pDTO = new OcrDTO();
            pDTO.setSeq(seq);

            OcrDTO rDTO = Optional.ofNullable(ocrService.getOcrInfo(pDTO)).orElseGet(OcrDTO::new);

            String fileName = rDTO.getOrgFileName();
            String saveFileName = rDTO.getFilePath() + "\\" + rDTO.getFileName();
            String ext = rDTO.getExt();

            File file = new File(saveFileName);
            Long fileLength = file.length();

            log.info("fileName : " + fileName);
            log.info("ext : " + ext);
            log.info("fileLength : " + fileLength);

            String encodedFilename = "attachment; filename*=" + "UTF-8" + "''" + URLEncoder.encode(fileName, "UTF-8");

            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", encodedFilename);
            response.setContentLengthLong(fileLength);

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

            try {
                byte[] buffer = new byte[4096];
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);

                }

                out.flush();

            } catch (Exception e) {
                log.error("다운로드 중 오류 발생: " + e.getMessage());
                response.setStatus(response.SC_INTERNAL_SERVER_ERROR);

            } finally {
                in.close();
                out.close();
            }


        } catch (Exception e) {
            log.info("error : " + e);
        }
    }
}
