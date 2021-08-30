package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
    private Status status;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")}
    )
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    @ManyToMany
    @JoinTable(name = "member_funding",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "funding_id", referencedColumnName = "funding_id")})
    @Builder.Default
    private List<Funding> fundingList = new ArrayList<>();

    public void joinCreator(Creator creator) {
        this.creator = creator;
        creator.setMember(this);
    }

    public void acceptCreator(Authority authority){
        this.status = Status.confirm;
        this.authorities.add(authority);
        creator.setStatus(Status.confirm);
    }

    public void participationFunding(Order order) {
        this.orders.add(order);
        order.addMember(this);
    }
    public void participationFunding(Order order) {
        this.orders.add(order);
        order.addMember(this);
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
