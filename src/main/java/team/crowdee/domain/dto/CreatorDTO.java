package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Status;

@Getter
@Setter
public class CreatorDTO {
    private Long creatorId;
    private Long memberId;
    private String creatorNickName;
    private String profileImgUrl;
    private String aboutMe;
    private String career;
    private String BusinessNumber;
    private String accountNumber;
    private String bankName;
    private String bankBookImageUrl;
    private String status;



}
