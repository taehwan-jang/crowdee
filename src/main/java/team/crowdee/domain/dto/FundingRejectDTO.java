package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FundingRejectDTO {
    private Long fundingId;
    private String reason;
}
