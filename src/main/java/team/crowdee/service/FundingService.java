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
import java.util.List;

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

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> findThumbNail() {
        List<ThumbNailDTO> thumbNailDTOList = new ArrayList<>();
        List<Funding> fundingList = fundingRepository.findAll();
        for (Funding funding : fundingList) {
            thumbNailDTOList.add(
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
                            .rateOfAchievement(funding.rateOfAchievement())
                            .build()
            );
        }
        return thumbNailDTOList;
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
