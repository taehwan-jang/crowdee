package team.crowdee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    private String userId;
    private String userName;
    private String nickName;
    private String gender;
    private int age;
    private String birth;
    private String phone;
    private String mobile;
    private String email;
    private LocalDateTime registDate;
    private LocalDateTime secessionDate;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Grade rank;

//    @OneToMany(mappedBy = "member")
//    @Builder.Default
//    private List<Follow> followers = new ArrayList<>(); // follow 다시 생각해봐

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

}
