package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailDTO {

    private Long fundingId;
    private String content;
    private String budget;
    private String schedule;
    private String aboutUs;
}
