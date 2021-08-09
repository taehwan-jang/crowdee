package team.crowdee.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Grade;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDTO {
    private String userId;
    private String userName;
    private String passWord;
    private String nickName;
    private String gender;
    private int age;
    private String birth;
    private String phone;
    private String mobile;
    private String email;
    private LocalDateTime registDate;
    private LocalDateTime secessionDate;
    private Address address;
    private Grade rank;
}
