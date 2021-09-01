package team.crowdee.domain.dto;

import lombok.*;
import team.crowdee.domain.Status;

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
    private String subTitle;
    private String summary;
    private String tag;
    private int rateOfAchievement;
    private int goalFundraising;
    private int totalFundraising;
    private String category;
    private Long restDate;
    private int participant;
    private Status status;

    private boolean isWish;

}
