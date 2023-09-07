package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.persistance.mapper.IMailMapper;
import kopo.poly.service.IMailService;
import kopo.poly.util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {

    private final JavaMailSender mailSender;

    private final IMailMapper mailMapper;

    @Value("${spring.mail.username}")
    private String fromMail;

    /* 메일 발송 */
    @Override
    public int doSendMail(MailDTO pDTO) throws Exception {

        log.info("메일 발송 실행");

        int res = 1;    // 메일 발송 성공여부 성공 : 1 / 실패 : 0

        // 전달 받은 DTO로부터 데이터 가져오기. DTO 객체가 메모리에 올라가지 않아 Null이 발생할 수 있기 때문에 에러방지 차원으로 if문 사용
        if (pDTO == null) {
            pDTO = new MailDTO();
        }

        String toMail = CmmUtil.nvl(pDTO.getToMail());
        String title = CmmUtil.nvl(pDTO.getTitle());
        String contents = CmmUtil.nvl(pDTO.getContents());

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);

        // 메일 발송 메시지 구조(파일 첨부 가능)
        MimeMessage message = mailSender.createMimeMessage();

        // 메일 발송 메시지 구조를 쉽게 생성하게 도와주는 객체
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try {
            messageHelper.setTo(toMail);
            messageHelper.setFrom(fromMail);
            messageHelper.setSubject(title);
            messageHelper.setText(contents);

            mailSender.send(message);

        } catch (Exception e) {
            res = 0;
            log.info("[ERROR] " + "메일 발송 결과 실패");

        }

        log.info("메일 발송 종료");

        mailMapper.insertSendMail(pDTO);

        return res;
    }

    @Override
    public List<MailDTO> getMailList() throws Exception {

        log.info(".service 메일 리스트");

        return mailMapper.getMailList();
    }

}
