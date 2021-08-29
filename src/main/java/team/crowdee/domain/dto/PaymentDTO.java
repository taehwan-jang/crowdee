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
    private String name;//상품명 -> funding title
    private int amount;//펀딩 참여금액
    private String buyer_email;//구매자 email
    private String buyer_name;//구매자 이름
    private String buyer_tel;//구매자의 핸드폰번호
    private String buyer_addr;//필요
    private String buyer_postcode;//없을거같은데
}
