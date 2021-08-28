package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminLoginDTO {

    private String email;
    private String password;
    private String admin;
}
