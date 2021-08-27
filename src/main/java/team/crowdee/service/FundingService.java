package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Funding;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingRepository;
import team.crowdee.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;

    /**
     * 명확한 영역 구분을 위해 펀딩의 등록 수정 삭제는 creatorService 에서 진행
     * FundingService - backer 의 Funding 참여에 수행하는 비즈니스로직
     */

    /**
     * map("new",ThumbNailDTO) 최신펀딩 가져오기(시작일기준)
     */

    /**
     * map("under",ThumbNailDTO) 달성직전 가져오기(참여율 기준으로 100미만인 펀딩)
     */

    /**
     * map("over",ThumbNailDTO) 초과펀딩 가져오기(참여율 기준으로 100초과인 펀딩 & maxBacker 여유)
     */

    /**
     * map("popular",ThumbNailDTO) 관심도순 가져오기(조회수)
     */

    @Transactional(readOnly = true)
    public Map<String,List<ThumbNailDTO>> findThumbNail() {
        Map<String,List<ThumbNailDTO>> map = new HashMap<>();

        List<ThumbNailDTO> newThumbNail = new ArrayList<>();
        List<Funding> fundingList1 = fundingRepository.findNewFunding();
        fundingToThumbNail(newThumbNail, fundingList1);

        List<ThumbNailDTO> underThumbNail = new ArrayList<>();
        List<Funding> fundingList2 = fundingRepository.findUnderFunding();
        fundingToThumbNail(underThumbNail, fundingList2);

        List<ThumbNailDTO> overThumbNail = new ArrayList<>();
        List<Funding> fundingList3 = fundingRepository.findOverFunding();
        fundingToThumbNail(overThumbNail, fundingList3);

        List<ThumbNailDTO> popularThumbNail = new ArrayList<>();
        List<Funding> fundingList4 = fundingRepository.findPopularFunding();
        fundingToThumbNail(popularThumbNail, fundingList4);

        map.put("new", newThumbNail);
        map.put("under", underThumbNail);
        map.put("over", overThumbNail);
        map.put("popular", popularThumbNail);

        return map;
    }

    private void fundingToThumbNail(List<ThumbNailDTO> newThumbNail, List<Funding> fundingList1) {
        for (Funding funding : fundingList1) {
            newThumbNail.add(
                    ThumbNailDTO.builder()
                            .fundingId(funding.getFundingId())
                            .creatorId(funding.getCreator().getCreatorId())
                            .projectUrl(funding.getProjectUrl())
                            .tag(funding.getTag())
                            .title(funding.getTitle())
                            .goalFundraising(funding.getGoalFundraising())
                            .totalFundraising(funding.getTotalFundraising())
                            .thumbNailUrl(funding.getThumbNailUrl())
                            .restDate(funding.getRestDays())
                            .summary(funding.getSummary())
                            .category(funding.getCategory())
                            .rateOfAchievement(funding.getRateOfAchievement())
                            .participant(funding.totalParticipant())
                            .build()
            );
        }
    }

    @Transactional(readOnly = true)
    public FundingDTO findOneFunding(String projectUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("projectUrl", projectUrl);
        if (fundingList.isEmpty()) {
            return null;
        }
        return Utils.fundingEToD(fundingList.get(0));
    }


}
