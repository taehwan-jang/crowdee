package team.crowdee.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EditingListDTO {

    private Long creatorId;
    private String title;
    private String postDate;
    private String manageUrl;

}
