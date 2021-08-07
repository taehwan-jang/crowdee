package team.crowdee.domain.valuetype;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class AccountInfo {

    private String accountNumber;
    private String bankName;
    private String accountImageUrl;
}