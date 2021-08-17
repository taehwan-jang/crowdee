package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassDTO {

    private Long memberId;
    private String email;
    private String oldPassword;
    private String newPassword;

}
