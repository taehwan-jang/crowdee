package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassDTO {

    private String userId;
    private String oldPassword;
    private String newPassword;

}
