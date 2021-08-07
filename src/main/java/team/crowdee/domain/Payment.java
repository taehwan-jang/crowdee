package team.crowdee.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne(mappedBy = "payment")
    private Order order;

    private LocalDateTime paymentDate;
    private int amount;
    //기타 결제 필요한 필드 생성------


}