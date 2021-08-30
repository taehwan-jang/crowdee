package team.crowdee.domain.dto;

import lombok.*;
import team.crowdee.domain.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminTokenDTO {
    private String token;
    private String authorityName;
}
