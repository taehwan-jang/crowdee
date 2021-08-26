package team.crowdee.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ThumbNailDTO {

    private Long fundingId;
    private Long creatorId;
    private String projectUrl;
    private String thumbNailUrl;
    private String title;
    private String summary;
    private String tag;
    private int rateOfAchievement;
    private int goalFundraising;
    private int totalFundraising;
    private String category;
    private int restDate;

}
