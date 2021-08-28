package team.crowdee.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PaymentDTO {

    private Long fundingId;
    private String init = "INIpayTest";
    private String pg = "kakaopay";
    private String pay_method = "card";
    private String merchant_uid = "merchant_" + new Date().getTime();
    private String name;
    private int amount;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_addr;//필요
    private String buyer_postcode;//없을거같은데
}
