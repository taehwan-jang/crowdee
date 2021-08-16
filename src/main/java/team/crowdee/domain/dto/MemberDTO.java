package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.UserState;

import java.time.LocalDateTime;
import java.util.Calendar;

@Getter @Setter
public class MemberDTO {

    private Long memberId;
    private String userName;
    private String password;
    private String nickName;
    private String mobile;
    private String email;
    private String emailCert;
    private LocalDateTime registDate;
    private LocalDateTime secessionDate;
    private UserState userState;


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