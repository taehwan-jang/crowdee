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
    private String aboutMe;
    private String career;
    private String BusinessNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "creator")
    private Member member;

    @OneToMany(mappedBy = "creator")
    private List<Funding> fundingList = new ArrayList<>();//creator 가 진행한 funding 리스트

    @Embedded
    private AccountInfo accountInfo;

    //푸쉬테스트
    //커밋, 푸쉬테스트
//    @OneToOne
//    @JoinColumn(name = "creator_inspection_id")
//    private CreatorInspection creatorInspection; //크리에이터 심사 기준 필요
}