package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.ThumbNail;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingCompRepository;
import team.crowdee.repository.FundingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FundingService {

    private final FundingRepository fundingRepository;
    private final FundingCompRepository fundingCompRepository;

    /**
     * 명확한 영역 구분을 위해 펀딩의 등록 수정 삭제는 creatorService 에서 진행
     * FundingService - backer 의 Funding 참여에 수행하는 비즈니스로직
     */

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> findThumbNail() {
        List<ThumbNail> thumbNailList = fundingCompRepository.findAllThumbNail();
        List<ThumbNailDTO> thumbNailDTOList = new ArrayList<>();
        for (ThumbNail thumbNail : thumbNailList) {
            thumbNailDTOList.add(
                    ThumbNailDTO.builder()
                            .fundingId(thumbNail.getFunding().getFundingId())
                            .title(thumbNail.getTitle())
                            .goalFundraising(thumbNail.getFunding().getFundingStatus().getTotalFundraising())
                            .thumbNailUrl(thumbNail.getThumbNailUrl())
                            .restDate(thumbNail.getFunding().getRestDays())
                            .summery(thumbNail.getSummery())
                            .category(thumbNail.getCategory())
                            .rateOfAchievement(thumbNail.getFunding().getFundingStatus().rateOfAchievement())
                            .build()
            );
        }
        return thumbNailDTOList;
    }



}
