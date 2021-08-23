package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FundingPlanDTO {

    private Long fundingId;
    private int goalFundraising;//목표금액
    private int minFundraising;//최소금액
    private String startDate;//시작일(yyyy-mm-dd)
    private String endDate;//종료일(yyyy-mm-dd)
    private int maxBacker;//최대후원자수

}
