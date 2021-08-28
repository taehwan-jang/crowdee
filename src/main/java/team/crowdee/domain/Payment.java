package team.crowdee.domain;

import lombok.*;
import team.crowdee.domain.dto.ReceiptDTO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne(mappedBy = "payment")
    private Order order;

    //아임포트 가맹점 정보
    private String init = "INIpayTest";
    //default PG사 카카오페이
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

    //=======결제가 완료된 후 값======//
    private String imp_uid;
    private String paid_amount;
    private String apply_num;

    public void receipt(ReceiptDTO receiptDTO) {
        this.imp_uid = receiptDTO.getImp_uid();
        this.paid_amount = receiptDTO.getPaid_amount();
        this.apply_num = receiptDTO.getApply_num();
    }

}