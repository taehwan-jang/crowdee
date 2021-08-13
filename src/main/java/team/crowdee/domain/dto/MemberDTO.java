package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberDTO {

    private Long memberId;
    private String userId;
    private String userName;
    private String password;
    private String nickName;
    private String gender;
    private int age;
    private String birth;
    private String phone;
    private String mobile;
    private String email;
    private LocalDateTime registDate;
    private LocalDateTime secessionDate;
    private String zonecode;
    private String roadAddress;
    private String restAddress;


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