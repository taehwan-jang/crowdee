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

    private Long funding_id;
    private Long creator_id;
    private String thumbNailUrl;
    private String title;
    private String summery;
    private String tag;
    private String rateOfAchievement;
    private int goalFundraising;
    private String category;
    private int restDate;

}
