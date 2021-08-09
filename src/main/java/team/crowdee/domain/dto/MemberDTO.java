package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class MemberDTO {

    private String userId;
    private String userName;
    private String pass;
    private String nickName;
    private String gender;
    private int age;
    private String birth;
    private String phone;
    private String mobile;
    private String email;
    private LocalDateTime registDate;

    private String zonecode;
    private String roadAddress;
    private String restAddress;

}