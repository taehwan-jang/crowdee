package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Funding;
import team.crowdee.domain.FundingStatus;
import team.crowdee.domain.ThumbNail;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingRepository;
import team.crowdee.repository.ThumbNailRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FundingService {


    private final FundingRepository fundingRepository;
    private final ThumbNailRepository thumbNailRepository;

    /**
     * 명확한 영역 구분을 위해 펀딩의 등록 수정 삭제는 creatorService 에서 진행
     * FundingService - backer 의 Funding 참여에 수행하는 비즈니스로직
     */

    public Funding changeFundingStatus(FundingStatus fundingStatus, Long id) {
        Funding findFunding = fundingRepository.findById(id);
        findFunding.changeFundingStatus(fundingStatus);

        return findFunding != null ? findFunding : null;
    }

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> findThumbNail() {
        List<ThumbNail> thumbNailList = thumbNailRepository.findAll();
        List<ThumbNailDTO> thumbNailDTOList = new ArrayList<>();
        for (ThumbNail thumbNail : thumbNailList) {
            thumbNailDTOList.add(
                    ThumbNailDTO.builder()
                            .funding_id(thumbNail.getFunding().getFundingId())
                            .title(thumbNail.getTitle())
                            .goalFundraising(thumbNail.getFunding().getGoalFundraising())
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
