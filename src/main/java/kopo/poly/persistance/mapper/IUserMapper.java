package kopo.poly.persistance.mapper;

import kopo.poly.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IUserMapper {

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

    /* 로그인 */
    UserDTO getLogin(UserDTO pDTO) throws Exception;

    /**
     * 아이디, 비밀번호 찾기에 활용
     * 1. 이름과 이메일이 맞다면, 아이디 알려주기
     * 2. 아이디, 이름과 이메일이 맞다면, 비밀번호 재설정하기
     */
    UserDTO getUserId(UserDTO pDTO) throws Exception;

    /* 비밀번호 재설정 */
    int updatePassword(UserDTO pDTO) throws Exception;
}
