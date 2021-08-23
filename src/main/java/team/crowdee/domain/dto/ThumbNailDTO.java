package team.crowdee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ThumbNailDTO {

    private Long fundingId;
    private Long creatorId;
    private String thumbNailUrl;
    private String title;
    private String summery;
    private String tag;
    private String rateOfAchievement;
    private int goalFundraising;
    private String category;
    private int restDate;

}
