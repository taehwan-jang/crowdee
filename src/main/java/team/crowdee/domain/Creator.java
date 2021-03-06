package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.AccountInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Setter
public class Creator {
    @Id
    @GeneratedValue
    @Column(name = "creator_id")
    private Long creatorId;
    private String creatorNickName;
    private String profileImgUrl;
    @Column(length = 2000)
    private String aboutMe;
    @Column(length = 2000)
    private String career;
    private String businessNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "creator")
    private Member member;

    @OneToMany(mappedBy = "creator")
    private List<Funding> fundingList = new ArrayList<>();//creator 가 진행한 funding 리스트

    @Embedded
    private AccountInfo accountInfo;

}