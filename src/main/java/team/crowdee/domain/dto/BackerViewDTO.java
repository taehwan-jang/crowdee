package team.crowdee.domain.dto;

import team.crowdee.domain.Authority;
import team.crowdee.domain.Status;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Getter
@Setter
public class BackerViewDTO {

    private Long memberId;
    //회원가입할때 아이디 패스워드 검증
    private String userName; //x
    private String nickName;//중복검사 o
    private String mobile; //o
    private String email; //x --애매한 부분
    private String emailCert; //이메일 인증 받았을때 //o
    private LocalDateTime registDate;
    private String secessionDate;
    private String authorities;
    private Status status;
}