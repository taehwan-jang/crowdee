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
    //위도경도 정보 추가

    private String Latitude; //위도
    private String longitude; //경도


}
