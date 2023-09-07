package kopo.poly.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String id; //아이디
    private String name;   // 이름
    private String pw;  //비번
    private String email;   // 이메일
    private String tel; //전화번호
}

