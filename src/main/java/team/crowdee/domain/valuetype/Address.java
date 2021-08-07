package team.crowdee.domain.valuetype;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Address {

    private String zonecode;
    private String roadAddress;
    private String restAddress;

}
