package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Status;

@Getter
@Setter
public class CreatorDTO {
    private Long creatorid;
    private Long memberId;
    private String creatorNickName;
    private String BusinessNumber;
    private String accountNumber;
    private String bankName;
    private String bankBookImageUrl;
    private String status;



}
