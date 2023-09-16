package kopo.poly.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserDTO {

    private String id;      //아이디
    private String name;    // 이름
    private String pw;      //비번
    private String email;   // 이메일
    private String addr1;   // 주소
    private String addr2;   // 상세주소
    private String regId;
    private String regDt;
    private String chgId;
    private String chgDt;

    private String userSeq;


    private String existsYn;// Y or N
    private int authNumber; // 이메일 중복체크 위한 인증번호
}

