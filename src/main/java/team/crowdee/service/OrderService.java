package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.Order;
import team.crowdee.domain.dto.WaitingPaymentDTO;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final FundingRepository fundingRepository;

    @Transactional(readOnly = true)
    public List<WaitingPaymentDTO> listUpWaitingPayment(String email) {
        List<Member> memberList = memberRepository.findByEmailWithOrderAndFunding(email);
        if (memberList.isEmpty()) {
            throw new IllegalArgumentException("정보가 없습니다");
        }
        Member member = memberList.get(0);
        List<Order> orders = member.getOrders();
        if (orders.isEmpty()) {
            throw new IllegalStateException("참여중인 펀딩이 없습니다.");
        }
        List<WaitingPaymentDTO> paymentDTOList = new ArrayList<>();
        for (Order order : orders) {
            paymentDTOList.add(
                    WaitingPaymentDTO.builder()
                    .fundingId(order.getFunding().getFundingId())
                    .title(order.getFunding().getTitle())
                    .summary(order.getFunding().getSummary())
                    .projectUrl(order.getFunding().getProjectUrl())
                    .thumbNailUrl(order.getFunding().getThumbNailUrl())
                    .amount(order.getPayment().getAmount())
                    .build()
            );
        }
        return paymentDTOList;
    }
}
