package team.crowdee.domain;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;
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
    @Builder.Default
    private String init = "imp62492999";
    //default PG사 카카오페이
    @Builder.Default
    private String pg = "kakaopay";
    @Builder.Default
    private String pay_method = "card";
    @Builder.Default
    private String merchant_uid = "merchant_" + new Date().getTime();

    private String name;
    private int amount;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_addr;//필요
    private String buyer_postcode;//없을거같은데

    //=======결제가 완료된 후 값======//
    @Builder.Default
    private String imp_uid="none";//고유id
    private String paid_amount;//결제금액
    private String apply_num;//승인번호

    public void receipt(ReceiptDTO receiptDTO) {
        this.imp_uid = receiptDTO.getImp_uid();
        this.paid_amount = receiptDTO.getPaid_amount();
        this.apply_num = receiptDTO.getApply_num();
    }

}