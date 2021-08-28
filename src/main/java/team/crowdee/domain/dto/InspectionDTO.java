package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InspectionDTO {
    private Long creatorId;
    private Long fundingId;
    private String status;
    private String summary;
    private String category;
    private String title;
    private String startDate;//시작일(yyyy-mm-dd)
    private String endDate;//종료일(yyyy-mm-dd)
}
