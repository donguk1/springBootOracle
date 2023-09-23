package kopo.poly.service.impl;

import kopo.poly.dto.MailDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.persistance.mapper.IUserMapper;
import kopo.poly.service.IMailService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final IUserMapper userMapper;
    private final IMailService mailService;


    /* 회원 가입하기 */
    @Override
    public int insertUser(UserDTO pDTO) throws Exception {

        log.info(".service 회원가입 실행");

        int res = 0; // 성공시 1, 아이디 중복으로 인한 취소 2, 기타 에러 0

        int success = userMapper.insertUser(pDTO);

        if (success > 0) {
            res = 1;

            MailDTO mailDTO = new MailDTO();
            mailDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));
            mailDTO.setTitle("회원가입을 축하드립니다.");
            mailDTO.setContents(CmmUtil.nvl(pDTO.getName() + "님의 회원가입을 진심으로 축하드립니다."));

            mailService.doSendMail(mailDTO);

        }

        log.info(".service 회원가입 종료");

        return res;
    }

    /* 아이디 중복체크 */
    @Override
    public UserDTO getIdExists(UserDTO pDTO) throws Exception {

        log.info(".service ID 중복체크 실행");

        UserDTO rDTO = userMapper.getIdExists(pDTO);

        log.info(".service ID 중복체크 종료");

        return rDTO;
    }

    /* 이메일 중복체크 */
    @Override
    public UserDTO getEmailExists(UserDTO pDTO) throws Exception {

        log.info(".service EmailAuth 실행");

        /*
         * DB에 이메일이 존재하는지 SQL 쿼리 실행
         * SQL 쿼리에 COUNT()를 사용하기 때문에 반드시 조회 결과는 존재함
         */
        UserDTO rDTO = userMapper.getEmailExists(pDTO);

        String existsYn = CmmUtil.nvl(rDTO.getExistsYn());

        log.info("existsYn : " + existsYn);

        if (existsYn.equals("N")) {

            // 6자리 랜덤 숫자 생성하기
            int authNumber = ThreadLocalRandom.current().nextInt(100000, 10000000);

            log.info("authNumber : " + authNumber);

            MailDTO mailDTO = new MailDTO();
            mailDTO.setTitle("이메일 중복 확인 인증번호 발송 메일");
            mailDTO.setContents("인증번호는 " + authNumber + "입니다.");
            mailDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));

            mailService.doSendMail(mailDTO);

            mailDTO = null;

            rDTO.setAuthNumber(authNumber);

        }

        log.info(".service EmailAuth 종료");

        return rDTO;
    }

    /* 유저 리스트 가져오기 */
    @Override
    public List<UserDTO> getUserList() throws Exception {

        log.info(".service 유저 리스트 가져오기 실행");

        return userMapper.getUserList();
    }

    /* 유저 상세보기 */
    @Override
    public UserDTO getUserInfo(UserDTO pDTO) throws Exception {

        log.info(".service 유저 상세보기 실행");

        return userMapper.getUserInfo(pDTO);
    }

    @Override
    public UserDTO getLogin(UserDTO pDTO) throws Exception {

        log.info(".service 로그인 시작");

        UserDTO rDTO = Optional.ofNullable(userMapper.getLogin(pDTO)).orElseGet(UserDTO::new);

//        if (CmmUtil.nvl(rDTO.getId()).length() > 0) {
//
//            MailDTO mDTO = new MailDTO();
//
//            mDTO.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(rDTO.getEmail())));
//
//            mDTO.setTitle("로그인 알림!");
//            mDTO.setContents(DateUtil.getDateTime("yyyy.MM.dd hh:mm:ss") + "에 " +
//                    CmmUtil.nvl(rDTO.getName()) + "님이 로그인 하였습니다.");
//
//            mailService.doSendMail(mDTO);
//
//        }

        log.info(".service 로그인 종료");

        return rDTO;
    }
}
