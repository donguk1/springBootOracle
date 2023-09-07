package kopo.poly.controller;


import kopo.poly.dto.MailDTO;
import kopo.poly.dto.MsgDTO;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/mail")
public class MailController {

    private final IMailService mailService;


    /* 메일 발송하기 폼 */
    @GetMapping(value = "mailForm")
    public String mailForm() throws Exception {

        log.info(".controller 메일폼 실행");

        return "/mail/mailForm";
    }

    @ResponseBody
    @PostMapping(value = "sendMail")
    public MsgDTO sendMail(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(".controller 메일 발송하기 실행");

        String msg = "";

        // 웹 URL로부터 전달받는 값들
        String toMail = CmmUtil.nvl(request.getParameter("toMail"));
        String title = CmmUtil.nvl(request.getParameter("title"));
        String contents = CmmUtil.nvl(request.getParameter("contents"));

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        MailDTO pDTO = new MailDTO();
        pDTO.setToMail(toMail);
        pDTO.setTitle(title);
        pDTO.setContents(contents);

        // 메일 발송 서비스 실행
        int res = mailService.doSendMail(pDTO);

        if (res == 1) { // 발송 성공
            msg = "메일 발송하였습니다.";
        } else {
            msg = "메일 발송 실패하였습니다.";
        }

        log.info(msg);

        MsgDTO dto = new MsgDTO();
        dto.setMsg(msg);

        log.info(".controller 메일 발송 종료");

        return dto;
    }


    @GetMapping(value = "mailList")
    public String mailList(ModelMap modelMap) throws Exception {

        log.info(".controller 메모 리스트 시작");

        List<MailDTO> rList = Optional.ofNullable(mailService.getMailList()).orElseGet(ArrayList::new);

        modelMap.addAttribute("rList", rList);

        log.info(".controller 메모 리스트 종료");

        return "/mail/mailList";

    }
}
