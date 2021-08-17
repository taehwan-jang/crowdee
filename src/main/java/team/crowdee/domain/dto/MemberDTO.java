package team.crowdee.domain.dto;

import lombok.*;
import team.crowdee.domain.Authority;
import team.crowdee.domain.UserState;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
public class MemberDTO {
    private Long memberId;
    //회원가입할때 아이디 패스워드 검증
    private String password; //o
    private String userName; //x
    private String nickName;//중복검사 o
    private String mobile; //o
    private String email; //x --애매한 부분
    private String emailCert; //이메일 인증 받았을때 //o
    private LocalDateTime registDate;
    private String secessionDate;
    @Enumerated(EnumType.STRING)
    private UserState userState;
    private Set<Authority> authorities;




    /**
     * 아이디
     * 닉네임 뺴야하는거 아닌가?
     * 비번 뺴고
     * 성별 뺴고
     * 나이 뺴고
     * 생일 뺴고
     *
     */
}