package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Funding;
import team.crowdee.domain.FundingStatus;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingRepository;

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

    public Funding changeFundingStatus(FundingStatus fundingStatus, Long id) {
        Funding findFunding = fundingRepository.findById(id);
        findFunding.changeFundingStatus(fundingStatus);

        return findFunding != null ? findFunding : null;
    }

    @Transactional(readOnly = true)
    public List<ThumbNailDTO> findThumbNail() {
        List<Funding> fundingList = fundingRepository.findAll();
        List<ThumbNailDTO> thumbNailList = new ArrayList<>();
        for (Funding funding : fundingList) {
            thumbNailList.add(ThumbNailDTO.builder()
                    .thumbNailUrl(funding.getThumbNailUrl())
                    .rateOfAchievement(funding.getStatus().rateOfAchievement())
                    .expiredDate(funding.getEndDate())
                    .restDate(funding.getRestDays())
                    .category(funding.getCategory())
                    .summery(funding.getSummery())
                    .title(funding.getTitle())
                    .build());
        }
        return thumbNailList;
    }



}
