package team.crowdee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingStatus {

    @Id
    @GeneratedValue
    @Column(name = "funding_status_id")
    private Long fundingStatusId;

    @OneToOne
    @JoinColumn(name = "funding_id")
    private Funding funding;

    private int visitCount;
    private int likeCount;
    private Status status;

    private int amountStatus;

    //멤버를 가지고 있는게 나을지 현재 후원자수가 몇명인지 아는게 좋을지 고민



}