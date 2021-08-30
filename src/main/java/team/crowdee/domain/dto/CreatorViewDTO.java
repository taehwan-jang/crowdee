package team.crowdee.domain.dto;

import lombok.*;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Status;
import team.crowdee.domain.UserState;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class CreatorViewDTO {

    private Long creatorID;
    private String creatorNickName;
    private String BusinessNumber;
    private String accountNumber;
    private String bankName;
    private String bankBookImageUrl;
    private String authorities;
    private Status status;

}