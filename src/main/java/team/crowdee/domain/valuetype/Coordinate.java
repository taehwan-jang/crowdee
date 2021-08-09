package team.crowdee.domain.valuetype;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Coordinate {

    private String Latitude; //위도
    private String longitude; //경도

}