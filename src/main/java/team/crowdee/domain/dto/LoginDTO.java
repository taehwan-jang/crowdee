package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import team.crowdee.domain.Withdrawal;

@Getter
@Setter
public class LoginDTO {
    private String userId;
    private String password;
    private Withdrawal withdrawal;
}
