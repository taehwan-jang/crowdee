package team.crowdee.domain.dto;

import team.crowdee.domain.Authority;
import team.crowdee.domain.Status;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Getter
@Setter

public class BackerAllDTO {
    private Long memberId;
    private String userName;
    private String email;
    private LocalDateTime registDate;
    private String authorities;
    private Status status;
}