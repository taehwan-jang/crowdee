package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.*;
import team.crowdee.domain.dto.FundingViewDTO;
import team.crowdee.domain.dto.PaymentDTO;
import team.crowdee.domain.dto.SimpleFundingListDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.repository.OrderRepository;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final MimeEmailService mimeEmailService;

    @Transactional(readOnly = true)
    public List<List<ThumbNailDTO>> mainThumbNail() {
        List<List<ThumbNailDTO>> thumbNailList = new ArrayList<>();

        List<Funding> fundingList1 = fundingRepository.findNewFunding(8);
        List<ThumbNailDTO> newThumbNail = Utils.fundingToThumbNail(fundingList1);

        List<Funding> fundingList2 = fundingRepository.findVergeOfSuccess(8);
        List<ThumbNailDTO> underThumbNail = Utils.fundingToThumbNail(fundingList2);

        List<Funding> fundingList3 = fundingRepository.findExcessFunding(8);
        List<ThumbNailDTO> overThumbNail = Utils.fundingToThumbNail(fundingList3);

        List<Funding> fundingList4 = fundingRepository.findPopularFunding(8);
        List<ThumbNailDTO> popularThumbNail = Utils.fundingToThumbNail(fundingList4);

        thumbNailList.add(newThumbNail);
        thumbNailList.add(underThumbNail);
        thumbNailList.add(overThumbNail);
        thumbNailList.add(popularThumbNail);

        return thumbNailList;
    }

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> tagView(String tag) {

        List<Funding> fundingList = fundingRepository.findByTag(tag);
        return Utils.fundingToThumbNail(fundingList);

    }

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> categoryView(String category) {
        List<Funding> fundingList = fundingRepository.findByParam("category",category);
        return Utils.fundingToThumbNail(fundingList);

    }

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> selectedMenu(String menu) {
        List<Funding> fundingList =null;
        switch (menu) {
            case "startDate"://시작일 최신순
                fundingList=fundingRepository.findNewFunding(100);
                break;
            case "visitCount"://조회수
                fundingList=fundingRepository.findPopularFunding(100);
                break;
            case "outOfStock"://0<restTicket <3 인 애
                fundingList=fundingRepository.findOutOfStock(100);
                break;
            case "vergeOfSuccess"://성공률 80~100사이
                fundingList = fundingRepository.findVergeOfSuccess(100);
                break;
            case "excess"://성공률 100% 초과
                fundingList=fundingRepository.findExcessFunding(100);
                break;
            default:
                return null;
        }
        return Utils.fundingToThumbNail(fundingList);

    }



    public FundingViewDTO findOneFunding(String projectUrl) {
        List<Funding> fundingList = fundingRepository.findByUrl(projectUrl);
        if (fundingList.isEmpty()) {
            throw new IllegalArgumentException("유효않은 ProjectUrl입니다.");
        }
        Funding funding = fundingList.get(0);
        funding.plusVisitCount();//조회수 증가
        Creator creator = funding.getCreator();
        List<Funding> creatorFundingList = fundingRepository.findByCreatorForIntroduce(creator.getCreatorId());
        List<SimpleFundingListDTO> simpleFundingList = new ArrayList<>();
        for (Funding funding1 : creatorFundingList) {
                simpleFundingList.add(
                        new SimpleFundingListDTO(
                                funding1.getProjectUrl(), funding1.getThumbNailUrl()
                        )
                );
        }
        return Utils.fundingEToD(funding,simpleFundingList);
    }

    public void participation(Long fundingId, PaymentDTO paymentDTO, String email) throws Exception {
        //참여 했던 펀딩인지 어떻게 확인하는가.
        List<Funding> fundingList = fundingRepository.findWithOrdersAndMember(fundingId);
        if (fundingList.isEmpty()) {
            throw new IllegalArgumentException("잘못된 펀딩번호 입니다.");
        }
        Funding funding = fundingList.get(0);
        List<Order> orders = funding.getOrders();
        for (Order order : orders) {
            if (order.getMember().getEmail().equals(email)) {
                throw new IllegalStateException("이미 참여한 펀딩입니다.");
            }
        }
        if (funding.getMinFundraising() > paymentDTO.getAmount()) {
            throw new IllegalArgumentException("최소 금액 이상 펀딩 가능합니다.");
        }
        List<Member> memberList = memberRepository.findByEmail(email);
        if (memberList.isEmpty()) {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
        Member member = memberList.get(0);
        mimeEmailService.joinFundingMail(member, funding);
        Payment payment = Payment.builder()
                .name(funding.getTitle())
                .amount(paymentDTO.getAmount())
                .buyer_email(paymentDTO.getBuyer_email())
                .buyer_name(paymentDTO.getBuyer_name())
                .buyer_tel(paymentDTO.getBuyer_tel())
                .buyer_addr(paymentDTO.getBuyer_addr())
                .buyer_postcode(paymentDTO.getBuyer_postcode())
                .build();
        orderRepository.paymentSave(payment);
        Order order = Order.builder()
                .funding(funding)
                .member(member)
                .orderDate(LocalDateTime.now())
                .payment(payment)
                .build();

        member.participationFunding(order);
        funding.addParticipants(order);
    }

    public boolean addOrRemoveMemberToFunding(String email, Long fundingId) {
        Funding funding = fundingRepository.findById(fundingId);
        List<Member> findMemberList = memberRepository.findByEmail(email);
        if (findMemberList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        Member requestMember = findMemberList.get(0);
        List<Member> memberList = funding.getMemberList();
        for (Member member : memberList) {
            if (member.getEmail().equals(email)) {
                funding.getMemberList().remove(requestMember);
                member.getFundingList().remove(funding);
                return false;
            }
        }
        funding.getMemberList().add(requestMember);
        requestMember.getFundingList().add(funding);
        return true;
    }
    @Scheduled(cron = "0 0 1 * * *") //0 0 1 * * *로 변경하면 하루마다 메소드 시작.
    public void changeFundingStatus() {
        String todayString = Utils.getTodayString();
        List<Funding> confirmList = fundingRepository.findConfirmAndProgress(Status.confirm,Status.progress);
        for (Funding funding : confirmList) {
            if (funding.getStartDate().equals(todayString)) {
                funding.changeStatus(Status.progress);
            } else if (LocalDate.parse(funding.getEndDate()).compareTo(LocalDate.now()) >= 1) {
                funding.changeStatus(Status.end);
            }
        }
    }
}
