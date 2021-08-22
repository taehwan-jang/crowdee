package team.crowdee.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThumbNail {

    @Id
    @GeneratedValue
    @Column(name = "thumbnail_id")
    private Long thumbNailId;

    //썸네일에서 보여줄 항목들
    private String title;
    private String summery;//요약
    private String thumbNailUrl;//file이름 -> 여러건일경우 리스트?
    private String category;//카테고리
    private String tag;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_id")
    private Funding funding;

    public void createFunding(Funding funding) {
        this.funding = funding;
    }



}
