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

    private String thumbNailUrl;
    private String title;
    private String summery;
    private String rateOfAchievement;
    private String expiredDate;
    private String category;
    private int restDate;

}
