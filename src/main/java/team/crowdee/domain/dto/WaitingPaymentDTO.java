package team.crowdee.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaitingPaymentDTO {

    private Long fundingId;
    private String title;
    private String summary;//요약
    private String thumbNailUrl;//file이름 -> 여러건일경우 리스트?
    private String projectUrl;
    private int amount;

}
