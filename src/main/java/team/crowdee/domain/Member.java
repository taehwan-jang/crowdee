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
    private LocalDateTime postDate;
    private LocalDateTime secessionDate;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Grade rank;

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
        creator.setMember(this);

    }
}
