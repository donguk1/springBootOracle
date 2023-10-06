package kopo.poly.controller;

import kopo.poly.dto.MsgDTO;
import kopo.poly.dto.UserDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.expression.spel.ast.OpOr;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    @GetMapping(value = "regForm")
    public String userRegForm() {

        log.info(".controller 회원가입 창으로 이동");

        return "/user/regForm";
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

    /* 비번 변경용 */
    @ResponseBody
    @PostMapping(value = "updatePwEmail")
    public UserDTO updatePwEmail(HttpServletRequest request) throws Exception {

        log.info(".controller 이메일 중복체크 실행");

        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("email : " + email);

        UserDTO pDTO = new UserDTO();
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserDTO rDTO = Optional.ofNullable(userService.updatePwEmail(pDTO)).orElseGet(UserDTO::new);

        log.info("123        " + rDTO.getAuthNumber());

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
    @GetMapping(value = "login")
    public String userLogin() {

        log.info(".controller 로그인 창으로 이동");

        return "/user/login";
    }

    /* 로그인 실행 */
    @ResponseBody
    @PostMapping(value = "loginProc")
    public MsgDTO loginProc(HttpServletRequest request, HttpSession session) {

        log.info(".controller 로그인 실행");

        int res = 0;  // 로그인 성공시 1, 불일치로 인한 실패 0, 시스템 에러 2
        String msg = "";
        MsgDTO dto = null;
        UserDTO pDTO = null;

        try {

            String id = CmmUtil.nvl(request.getParameter("id"));
            String pw = CmmUtil.nvl(request.getParameter("pw"));

            log.info("id : " + id);
            log.info("pw : " + pw);

            pDTO = new UserDTO();
            pDTO.setId(id);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));

            UserDTO rDTO = userService.getLogin(pDTO);

            if (CmmUtil.nvl(rDTO.getId()).length() > 0 ) {  // 로그인 성공

                res = 1;
                msg = "로그인 성공했습니다.";

                session.setAttribute("SS_ID", id);
                session.setAttribute("SS_NAME", CmmUtil.nvl(rDTO.getName()));

            } else {
                msg = "아이디와 비밀번호가 올바르지 않습니다.";
            }

        } catch (Exception e) {

            res = 2;
            msg = "시스템 문제로 로그인이 실패 했습니다.";
            log.info(e.toString());
            e.printStackTrace();

        } finally {

            dto = new MsgDTO();
            dto.setResult(res);
            dto.setMsg(msg);

            log.info(".controller 로그인 종료");
        }

        return dto;
    }

    /* 유저 리스트 가져오기 */
    @GetMapping(value = "list")
    public String userList(ModelMap modelMap) throws Exception {

        log.info(".controller 유저 리스트 실행");

        List<UserDTO> rList = Optional.ofNullable(userService.getUserList()).orElseGet(ArrayList::new);

        modelMap.addAttribute("rList", rList);

        log.info(".controller 유저 리스트 종료");

        return "/user/list";
    }

    /* 유저 상세보기 */
    @GetMapping(value = "info")
    public String userInfo(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(".controller 유저 상세보기 실행");

        String id = CmmUtil.nvl(request.getParameter("id")); // PK 유저 유일값

        log.info("id : " + id);

        UserDTO pDTO = new UserDTO();
        pDTO.setId(id);

        UserDTO rDTO = Optional.ofNullable(userService.getUserInfo(pDTO)).orElseGet(UserDTO::new);

        modelMap.addAttribute("rDTO", rDTO);

        log.info(".controller 유저 상세보기 종료");

        return "/user/info";
    }

    /* 로그인 후 이동 */
    @GetMapping(value = "loginResult")
    public String lorinResult() {

        log.info(".controller 로그인 확인창 실행");

        return "/user/loginResult";

    }

    /* 로그인 정보 */
    @GetMapping(value = "loginInfo")
    public String loginInfo(HttpSession session, ModelMap modelMap) throws Exception {

        log.info(".controller 로그인시 유저 정보 실행");

        String id = CmmUtil.nvl((String) session.getAttribute("SS_ID"));

        UserDTO pDTO = new UserDTO();
        pDTO.setId(id);

        log.info("id : " + id);

        UserDTO rDTO = Optional.ofNullable(userService.getUserInfo(pDTO)).orElseGet(UserDTO::new);

        modelMap.addAttribute("rDTO", rDTO);

        log.info(".controller 로그인시 유저 정보 종료");

        return "/user/loginInfo";
    }

    /**
     * 아이디, 비밀번호 찾기에 활용
     * 1. 이름과 이메일이 맞다면, 아이디 알려주기
     * 2. 아이디, 이름과 이메일이 맞다면, 비밀번호 재설정하기
     */
    @GetMapping(value = "searchUserId")
    public String searchUserId() {

        log.info(".controller 아이디 비번 찾기 창 이동");

        return "user/searchUserId";

    }

    /* 아이디, 비번 찾기 로직 */
    @PostMapping(value = "searchUserIdProc")
    public String searchIdProc(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(".controller 아이디 비번 찾기 실행");

        String name = CmmUtil.nvl(request.getParameter("name"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("name : " + name);
        log.info("email : " + email);

        UserDTO pDTO = new UserDTO();
        pDTO.setName(name);
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserDTO rDTO = Optional.ofNullable(userService.getUserId(pDTO)).orElseGet(UserDTO::new);

        modelMap.addAttribute("rDTO", rDTO);

        log.info(".controller 아이디 비번 찾기 종료");

        return "user/searchUserIdResult";

    }

    /* 비밀번호 재설정 */
    @GetMapping(value = "searchPassword")
    public String searchPassword(HttpSession session) {

        log.info(".controller 비번 재설정 창 이동");

        session.setAttribute("NEW_PASSWORD", "");
        session.removeAttribute("NEW_PASSWORD");

        return "user/searchPassword";
    }

    /* 비번 찾기 로직 */
    @PostMapping(value = "searchPasswordProc")
    public String searchPasswordProc(HttpServletRequest request, ModelMap modelMap, HttpSession session) throws Exception {

        log.info(".controller 비번 찾기 실행");

        String id = CmmUtil.nvl(request.getParameter("id"));
        String name = CmmUtil.nvl(request.getParameter("name"));
        String email = CmmUtil.nvl(request.getParameter("email"));

        log.info("id : " + id);
        log.info("name : " + name);
        log.info("email : " + email);

        UserDTO pDTO = new UserDTO();
        pDTO.setId(id);
        pDTO.setName(name);
        pDTO.setEmail(EncryptUtil.encAES128CBC(email));

        UserDTO rDTO = Optional.ofNullable(userService.getUserId(pDTO)).orElseGet(UserDTO::new);

        modelMap.addAttribute("rDTO", rDTO);

        session.setAttribute("NEW_PASSWORD", id);

        log.info(".controller 비번 찾기 완료");

        return "user/newPassword";
    }

    /* 비번 재설정 로직 */
    @PostMapping(value = "newPasswordProc")
    public String newPasswordProc(HttpServletRequest request, ModelMap modelMap, HttpSession session) throws Exception {

        log.info(".controller 비번 재설정 실행");

        String msg = "";

        String newPassword = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

        if (newPassword.length() > 0) {
            String pw = CmmUtil.nvl(request.getParameter("pw"));

            log.info("pw : " + pw);

            UserDTO pDTO = new UserDTO();
            pDTO.setId(newPassword);
            pDTO.setPw(EncryptUtil.encHashSHA256(pw));

            userService.updatePassword(pDTO);

            session.setAttribute("NEW_PASSWORD", "");
            session.removeAttribute("NEW_PASSWORD");

            msg = "비밀번호가 재설정되었습니다.";

        } else {
            msg = "비정상 접근입니다.";

        }

        modelMap.addAttribute("msg", msg);

        log.info(".controller 비번 재설정 완료");

        return "user/newPasswordResult";
    }
}
