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

    @OneToOne(mappedBy = "funding")
    private ThumbNail thumbNail;


    @Lob
    private String content;//내용-> editor api를 사용해서 정보를 저장한다?

    //사이드바에 노출될 항목
    @OneToOne(mappedBy = "funding")
    private FundingPlan fundingPlan;
    private LocalDateTime postDate;//등록일

    @Embedded
    private Address address;//공연장 주소

    @OneToOne(mappedBy = "funding",fetch = FetchType.EAGER)
    private FundingStatus fundingStatus;//상태 엔티티

    @Enumerated(EnumType.STRING)
    private Status status;//현재 펀딩의 상태(심사/거절/진행/종료)

    @OneToMany(mappedBy = "funding")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();


    //=======Setter 대용=======//
    public void addFundingPlan(FundingPlan fundingPlan) {
        this.fundingPlan = fundingPlan;
    }

    public Funding changeFundingStatus(FundingStatus fundingStatus) {
        this.fundingStatus = fundingStatus;
        return this;
    }

    public Funding changeHall(Address address) {
        this.address = address;
        return this;
    }


    //==========조회용 로직 일부 추가===========//
    public int totalParticipant() {
        return getOrders().size();
    }

    public int getRestDays() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.parse(fundingPlan.getEndDate(), DateTimeFormatter.ISO_DATE);

        return Period.between(start, end).getDays();
    }

}