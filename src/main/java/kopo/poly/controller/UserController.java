package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final IUserService userService;

    /* 회원가입 창으로 이동  */
    @GetMapping(value = "userRegForm")
    public String userRegForm() {

        log.info(".controller 회원가입 창으로 이동");

        return "/user/userRegForm";
    }

    /* 아이디 중복체크 */
    @ResponseBody
    @PostMapping(value = "getIdExists")
    public UserDTO getIdExists(HttpServletRequest request) throws Exception {

        log.info(".controller ID 중복체크 실행");

        String id = CmmUtil.nvl(request.getParameter("id"));

        log.info("id : " + id);

        UserDTO pDTO = new UserDTO();
        pDTO.setId(id);

        UserDTO rDTO = Optional.ofNullable(userService.getIdExists(pDTO)).orElseGet(UserDTO::new);

        log.info(".controller ID 중복체크 종료");

        return rDTO;
    }

    /* 이메일 중복체크 */
    @ResponseBody
    @PostMapping(value = "getEmailExists")
    public UserDTO getEmailExists(HttpServletRequest request) throws Exception {

        log.info(".controller 이메일 중복체크 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserDTO pDTO = new UserDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserDTO rDTO = Optional.ofNullable(userService.getEmailExists(pDTO)).orElseGet(UserDTO::new);

        log.info(".controller 이메일 중복체크 종료");

        return rDTO;
    }

    /* 회원가입 */
    @ResponseBody
    @PostMapping(value = "insertUser")
    public MsgDTO insertUser(HttpServletRequest request) throws Exception {

        log.info(".controller 회원가입 실행");

        int res = 0;    //// 성공시 1, 아이디 중복으로 인한 취소 2, 기타 에러 0
        String msg = "";
        MsgDTO dto = null;
        UserDTO pDTO = null;

        try {

            String id = CmmUtil.nvl(request.getParameter("id")); //아이디
            String name = CmmUtil.nvl(request.getParameter("name")); //이름
            String pw = CmmUtil.nvl(request.getParameter("pw")); //비번
            String email = CmmUtil.nvl(request.getParameter("email")); //이메일
            String addr1 = CmmUtil.nvl(request.getParameter("addr1")); //주소
            String addr2 = CmmUtil.nvl(request.getParameter("addr2")); //상세주소

            log.info("id : " + id);
            log.info("name : " + name);
            log.info("pw : " + pw);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);

            pDTO = new UserDTO();
            pDTO.setId(id);
            pDTO.setName(name);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));
            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            res = userService.insertUser(pDTO);

            log.info("회원가입 결과(res) : " + res);  //// 성공시 1, 아이디 중복으로 인한 취소 2, 기타 에러 0

            if (res == 1) {
                msg = "회원가입 되었습니다.";

            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";

            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";

            }

        } catch (Exception e) {

            msg = "실패하였습니다.";
            log.info(e.toString());
            e.printStackTrace();

        } finally {

            dto = new MsgDTO();
            dto.setMsg(msg);

            log.info(".controller 회원가입 종료");
        }

        return dto;

    }

    /* 로그인 창으로 이동 */
    @GetMapping(value = "userLogin")
    public String userLogin() {

        log.info(".controller 로그인 창으로 이동");

        return "/user/userLogin";
    }

    /* 로그인 실행 */

    /* 유저 리스트 가져오기 */
    @GetMapping(value = "userList")
    public String userList(ModelMap modelMap) throws Exception {

        log.info(".controller 유저 리스트 실행");

        List<UserDTO> rList = Optional.ofNullable(userService.getUserList()).orElseGet(ArrayList::new);

        modelMap.addAttribute("rList", rList);

        log.info(".controller 유저 리스트 종료");

        return "/user/userList";
    }

    /* 유저 상세보기 */
    @GetMapping(value = "userInfo")
    public String userInfo(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(".controller 유저 상세보기 실행");

        String id = CmmUtil.nvl(request.getParameter("id")); // PK 유저 유일값

        log.info("id : " + id);

        UserDTO pDTO = new UserDTO();
        pDTO.setId(id);

        UserDTO rDTO = Optional.ofNullable(userService.getUserInfo(pDTO)).orElseGet(UserDTO::new);

        modelMap.addAttribute("rDTO", rDTO);

        log.info(".controller 유저 상세보기 종료");

        return "user/userInfo";
    }

}
