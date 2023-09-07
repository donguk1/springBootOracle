package kopo.poly.persistance.mapper;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IMailMapper {


    /* 메일 발송 저장 */
    void insertSendMail(MailDTO pDTO) throws Exception;

    List<MailDTO> getMailList() throws Exception;
}
