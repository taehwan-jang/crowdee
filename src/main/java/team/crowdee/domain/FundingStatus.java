package team.crowdee.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class FundingStatus {

    @Id
    @GeneratedValue
    @Column(name = "funding_status_id")
    private Long fundingStatusId;

    @OneToOne
    @JoinColumn(name = "funding_id")
    private Funding funding;//현재 진행중인 펀딩

    private int visitCount;//방문횟수(조회수)
    private int likeCount;//좋아요 갯수?? 찜으로 바꿀것

    @Enumerated(EnumType.STRING)
    private Status status;//현재 펀딩의 상태

    private int totalFundraising=0;//총 펀딩금액

    //멤버를 가지고 있는게 나을지 현재 후원자수가 몇명인지 아는게 좋을지 고민

    //펀딩 달성률 총 펀딩금액 / 목푶금액 * 100 %
    public String rateOfAchievement() {
        double rawRate = (double)this.totalFundraising / (double) funding.getGoalFundraising();
        //NPE 방지로 valueOf 사용
        String stringResult = String.valueOf(rawRate);
        return stringResult.substring(0, stringResult.lastIndexOf(".") + 2)+" %";
    }

    //방문횟수 추가
    public void plusVisitCount() {
        this.visitCount += 1;
    }

    //펀딩 참여시 총 펀딩금액 추가
    public void plusTotalFundraising(int amount) {
        this.totalFundraising += amount;
    }


}