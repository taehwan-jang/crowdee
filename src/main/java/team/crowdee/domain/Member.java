package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;
    //회원가입할때 아이디 패스워드 검증
    private String userId;//디비랑 중복검사
    private String password;
    private String userName;
    private String nickName;//중복검사
    private String gender;
    private int age;
    private String birth;
    private String phone;
    private String mobile;
    private String email;
    private String emailCert;
    private LocalDateTime registDate;
    private LocalDateTime secessionDate;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Authorities authorities;

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
        this.authorities = Authorities.creator;
        creator.setMember(this);

    }
    public void secessionMember(LocalDateTime secessionDate) {
        this.secessionDate = secessionDate;
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

    public Member changePhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Member changeMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public Member changeAddress(Address address) {
        this.address = address;
        return this;
    }




}
