package team.crowdee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.crowdee.domain.valuetype.AccountInfo;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creator {

    @Id
    @GeneratedValue
    @Column(name = "creator_id")
    private Long creatorId;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "creator")
    private List<Funding> fundings;//이건 creator 로 이동할것

    @Embedded
    private AccountInfo accountInfo;

//    @OneToOne
//    @JoinColumn(name = "creator_inspection_id")
//    private CreatorInspection creatorInspection; //크리에이터 심사 기준 필요
}