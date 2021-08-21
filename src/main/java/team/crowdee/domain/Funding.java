package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.domain.valuetype.Coordinate;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "thumbnail_id")
    private ThumbNail thumbNail;



    //검색전용


    @Lob
    private String content;//내용-> editor api를 사용해서 정보를 저장한다?

    //사이드바에 노출될 항목
    private int targetAmount;//목표금액
    private int minAmount;//최소금액
    private LocalDateTime postDate;//등록일
    private String startDate;//시작일(yyyy-mm-dd)
    private String endDate;//종료일(yyyy-mm-dd)
    private int maxBacker;//최대후원자수


    @Embedded
    private Address address;//공연장 주소

    @OneToOne(mappedBy = "funding")
    private FundingStatus status;//상태 엔티티

    @OneToMany(mappedBy = "funding")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();


    public void changeFundingStatus(FundingStatus status) {
        this.status = status;
    }

    public int getRestDays() {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        return Period.between(start, end).getDays();
    }

    public Funding changeHall(Address address) {
        this.address = address;
        return this;
    }

    public int totalParticipant() {
        return getOrders().size();
    }

}