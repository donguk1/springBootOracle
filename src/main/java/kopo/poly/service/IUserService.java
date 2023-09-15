package kopo.poly.service;

import kopo.poly.dto.UserDTO;

import java.util.List;

public interface IUserService {

    /* 회원 가입하기 */
    int insertUser(UserDTO pDTO) throws Exception;

    /* 아이디 중복체크 */
    UserDTO getIdExists(UserDTO pDTO) throws Exception;

    /* 이메일 중복체크 */
    UserDTO getEmailExists(UserDTO pDTO) throws Exception;

    /* 유저 리스트 가져오기 */
    List<UserDTO> getUserList() throws Exception;

    /* 유저 상세보기 */
    UserDTO getUserInfo(UserDTO pDTO) throws Exception;
}
