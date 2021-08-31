package team.crowdee.domain.dto;

import lombok.*;
import team.crowdee.domain.Authority;
import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
public class CreatorBackDTO {
    private Long memberId;
    //회원가입할때 아이디 패스워드 검증
    private String userName; //x
    private String nickName;//중복검사 o
    private String mobile; //o
    private String email; //x --애매한 부분
    private String emailCert; //이메일 인증 받았을때 //o
    private LocalDateTime registDate;
    private String secessionDate;
    private Set<Authority> authorities;
    private String creatorNickName;
    private String BusinessNumber;
    private String accountNumber;
    private String bankName;
    private String bankBookImageUrl;
    //팔로워
    //등록펀딩목록

}