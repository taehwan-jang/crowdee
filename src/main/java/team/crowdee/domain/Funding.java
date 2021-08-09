package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.domain.valuetype.Coordinate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Funding {

    @Id
    @GeneratedValue
    @Column(name = "funding_id")
    private Long fundingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Creator creator;

    private String title;
    private int targetAmount;//목표금액
    private int minAmount;//최소금액
    private LocalDateTime postDate;//등록일
    private LocalDateTime expiredDate;//만료일
    private int maxBacker;//최대후원자수

    @Lob
    private String content;//내용-> editor api를 사용해서 정보를 저장한다?
    private String summery;//요약
    private String imageName;//file이름 -> 여러건일경우 리스트?
    private String videoUrl;//소개 youtube 이름?

    @Embedded
    private Address address;//공연장 주소
    @Embedded
    private Coordinate coordinate;//지도 api전용 위/경도
    @OneToOne(mappedBy = "funding")
    private FundingStatus status;//상태 엔티티

    @OneToMany(mappedBy = "funding")
    private List<Order> orders;
}