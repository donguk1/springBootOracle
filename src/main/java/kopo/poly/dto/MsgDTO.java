package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class MsgDTO {

    /**
     * 내부에 숫자(int)가 있기에
     * @JsonInclude 내부에 NON_DEFAULT 추가 선언
     */

    private int result; // 성공 : 1 / 실패 : 그 외
    private String msg; // 메시지
}
