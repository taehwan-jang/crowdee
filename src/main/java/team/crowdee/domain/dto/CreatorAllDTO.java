package team.crowdee.domain.dto;
import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Status;

import java.util.Set;

@Getter
@Setter
public class CreatorAllDTO {
    private Long CreatorId;
    private String creatorNickName;
    private String BusinessNumber;
    private String accountNumber;
    private String authorities;
    private Status status;



}
