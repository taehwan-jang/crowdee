package team.crowdee.domain.dto;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Status;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Getter
@Setter
public class FundingAllDTO {

    private Long fundingId;
    private String title;
    private String summary;//요약
    private String category;
    private LocalDateTime postDate;
    private Status status;
}
