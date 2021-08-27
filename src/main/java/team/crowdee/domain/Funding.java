package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.valuetype.Address;

import javax.persistence.*;
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

    /**
     * 조회를 위한 project Url과 관리를 위한 management Url 생성
     */
    private String projectUrl;
    private String manageUrl;

    /**
     * 썸네일 정보
     */
    private String title;
    private String summary;//요약
    private String thumbNailUrl;//file이름 -> 여러건일경우 리스트?
    private String category;//카테고리
    private String tag;
    private int rateOfAchievement=0;

    /**
     * 메인 컨텐츠 (Detail)
     */
    private String content;
    private String budget;
    private String schedule;
    private String aboutUs;

    /**
     * 사이드바에 노출 할 항목
     */
    private int goalFundraising;//목표금액
    private int minFundraising;//최소금액
    private String startDate;//시작일(yyyy-mm-dd)
    private String endDate;//종료일(yyyy-mm-dd)
    private int maxBacker;//최대후원자수

    private LocalDateTime postDate;//등록일

    @Embedded
    private Address address;//공연장 주소

    /**
     * 펀딩의 상태
     */
    private int visitCount;//방문횟수(조회수)
    private int likeCount;//좋아요 갯수?? 찜으로 바꿀것
    private int totalFundraising = 0;//총 펀딩금액


    @Enumerated(EnumType.STRING)
    private Status status;//현재 펀딩의 상태(심사/거절/진행/종료)

    @OneToMany(mappedBy = "funding")
    @Builder.Default

    private List<Order> orders = new ArrayList<>();


    //=======Setter 대용=======//
    public Funding thumbTitle(String title) {
        this.title = title;
        return this;
    }

    public Funding thumbUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
        return this;
    }

    public Funding thumbCategory(String category) {
        this.category = category;
        return this;
    }

    public Funding thumbTag(String tag) {
        this.tag = tag;
        return this;
    }

    public Funding thumbSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Funding planGoalFundraising(int goalFundraising) {
        this.goalFundraising = goalFundraising;
        return this;
    }

    public Funding planStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }
    public Funding planEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
    public Funding planMinFundraising(int minFundraising) {
        this.minFundraising = minFundraising;
        return this;
    }
    public Funding planMaxBacker(int maxBacker) {
        this.maxBacker = maxBacker;
        return this;
    }
    public Funding detailContent(String content) {
        this.content = content;
        return this;
    }

    public Funding detailBudget(String budget) {
        this.budget = budget;
        return this;
    }
    public Funding detailSchedule(String schedule){
        this.schedule = schedule;
        return this;
    }

    public Funding detailAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
        return this;
    }




    public Funding changeStatus(Status status) {
        this.status = status;
        return this;
    }

    //필요한지 다시 한번 확인할것
    public Funding changeAddress(Address address) {
        this.address = address;
        return this;
    }

    //==========조회용 로직 일부 추가===========//
    public int totalParticipant() {

        return getOrders().size();
    }

    public int getRestDays() {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.parse(this.endDate, DateTimeFormatter.ISO_DATE);

        return Period.between(start, end).getDays();
    }

    public void increaseAchievement() {
        int rawRate = (int)(((double) this.totalFundraising / (double) this.goalFundraising)*100);
        this.rateOfAchievement = rawRate;
    }

    //방문횟수 추가
    public void plusVisitCount() {
        this.visitCount += 1;
    }

    //펀딩 참여시 총 펀딩금액 추가
    public void plusTotalFundraising(int amount) {
        this.totalFundraising += amount;
        increaseAchievement();
    }

}