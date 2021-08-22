package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
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

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Follow> following = new ArrayList<>(); // follow 다시 생각해봐

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;


    public void joinCreator(Creator creator) {
        this.creator = creator;
        this.userState = UserState.creator;
        creator.setMember(this);

    }

    //=====수정을 위한 패턴=====//

    public Member changePassword(String password) {
        this.password = password;
        return this;
    }

    public Member changeNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public Member changeMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Member changeSecessionDate(String secessionDate) {
        this.secessionDate = secessionDate;
        return this;
    }

}
