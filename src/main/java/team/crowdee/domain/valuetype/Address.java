package team.crowdee.domain.valuetype;

import lombok.*;
import team.crowdee.domain.dto.MemberDTO;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Address {
    private String zonecode;
    private String roadAddress;
    private String restAddress;


}
