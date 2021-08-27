package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Funding;
import team.crowdee.repository.CreatorRepository;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.MemberRepository;
import team.crowdee.repository.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final FundingRepository fundingRepository;
    private final OrderRepository orderRepository;

    /**
     * 펀딩 상태 변경 로직
     */
    public List<Funding> inspectionList() {
        List<Funding> toInspection = fundingRepository.findToInspection();
        /**
         * 위 toInspection 에 funding 들이 담겨있고 creator 도 포함되어 있음.
         * 필요한 데이터 DTO에 담아서 list로 뿌려줄것
         */
        return null;
    }


}
