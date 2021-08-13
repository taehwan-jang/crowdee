package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.AccountInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Creator {

    @Id
    @GeneratedValue
    @Column(name = "creator_id")
    private Long creatorId;

    private String BusinessNumber;

    @OneToOne(mappedBy = "creator", cascade = CascadeType.ALL)
    private Member member;

    @OneToMany(mappedBy = "creator")
    @Builder.Default
    private List<Follow> follows = new ArrayList<>();


    @OneToMany(mappedBy = "creator")
    private List<Funding> fundings;//이건 creator 로 이동할것

    @Embedded
    private AccountInfo accountInfo;

    //푸쉬테스트
    //커밋, 푸쉬테스트
//    @OneToOne
//    @JoinColumn(name = "creator_inspection_id")
//    private CreatorInspection creatorInspection; //크리에이터 심사 기준 필요
}