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
    public List<List<ThumbNailDTO>> mainThumbNail() {
        List<List<ThumbNailDTO>> thumbNailList = new ArrayList<>();

        List<ThumbNailDTO> newThumbNail = new ArrayList<>();
        List<Funding> fundingList1 = fundingRepository.findNewFunding(4);
        fundingToThumbNail(newThumbNail, fundingList1);

        List<ThumbNailDTO> underThumbNail = new ArrayList<>();
        List<Funding> fundingList2 = fundingRepository.findVergeOfSuccess(4);
        fundingToThumbNail(underThumbNail, fundingList2);

        List<ThumbNailDTO> overThumbNail = new ArrayList<>();
        List<Funding> fundingList3 = fundingRepository.findExcessFunding(4);
        fundingToThumbNail(overThumbNail, fundingList3);

        List<ThumbNailDTO> popularThumbNail = new ArrayList<>();
        List<Funding> fundingList4 = fundingRepository.findPopularFunding(4);
        fundingToThumbNail(popularThumbNail, fundingList4);

        thumbNailList.add(newThumbNail);
        thumbNailList.add(underThumbNail);
        thumbNailList.add(overThumbNail);
        thumbNailList.add(popularThumbNail);

        return thumbNailList;
    }

    public List<ThumbNailDTO> tagView(String tag) {

        List<Funding> fundingList = fundingRepository.findByTag(tag);
        List<ThumbNailDTO> tagThumbNail = new ArrayList<>();
        fundingToThumbNail(tagThumbNail, fundingList);
        return tagThumbNail;
    }

    public List<ThumbNailDTO> categoryView(String category) {
        List<Funding> fundingList = fundingRepository.findByParam("category",category);
        List<ThumbNailDTO> categoryResult = new ArrayList<>();
        fundingToThumbNail(categoryResult, fundingList);
        return categoryResult;
    }

    public List<ThumbNailDTO> selectedMenu(String menu) {
        List<Funding> fundingList =null;
        switch (menu) {
            case "startDate"://시작일 최신순
                fundingList=fundingRepository.findNewFunding(100);
                break;
            case "visitCount"://조회수
                fundingList=fundingRepository.findPopularFunding(100);
                break;
            case "outOfStock"://order.size+5 <= maxBacker
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
        List<ThumbNailDTO> thumbNailDTOList = new ArrayList<>();
        fundingToThumbNail(thumbNailDTOList,fundingList);
        return thumbNailDTOList;
    }

    private void fundingToThumbNail(List<ThumbNailDTO> thumbNailDTOList, List<Funding> fundingList1) {
        for (Funding funding : fundingList1) {
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
                            .rateOfAchievement(funding.getRateOfAchievement())
                            .participant(funding.totalParticipant())
                            .build()
            );
        }
    }

    public FundingDTO findOneFunding(String projectUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("projectUrl", projectUrl);
        if (fundingList.isEmpty()) {
            return null;
        }
        fundingList.get(0).plusVisitCount();//조회수 증가
        return Utils.fundingEToD(fundingList.get(0));
    }



}
