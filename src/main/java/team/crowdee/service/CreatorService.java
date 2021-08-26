package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import team.crowdee.domain.valuetype.AccountInfo;
import team.crowdee.repository.*;
import team.crowdee.util.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CreatorService {

    private final MemberRepository memberRepository;
    private final CreatorRepository creatorRepository;
    private final FundingRepository fundingRepository;

    public Creator joinCreator(CreatorDTO creatorDTO){
        /**
         * 검증 시작
         * 소셜 가입 회원인 경우 추가 정보를 입력하게만들기~
         */
        boolean result = validation(creatorDTO);
        if (!result) {
            throw new IllegalArgumentException("빈값이 있다.");
        }
        Member member = memberRepository.findById(creatorDTO.getMemberId());
        AccountInfo account = new AccountInfo();
        account.setAccountNumber(creatorDTO.getAccountNumber());
        account.setBankBookImageUrl(creatorDTO.getBankBookImageUrl());
        account.setBankName(creatorDTO.getBankName());
        Creator creator = Creator.builder()
                .status(Status.inspection)
                .creatorNickName(creatorDTO.getCreatorNickName())
                .BusinessNumber(creatorDTO.getBusinessNumber())
                .accountInfo(account)
                .build();
        creatorRepository.save(creator);
        member.joinCreator(creator);
        return creator;
    }

    public Creator findCreator(Long creatorId) {
        return creatorRepository.findById(creatorId);
    }

    //검증은 한번에 진행
    public boolean validation(CreatorDTO creatorDTO) {
        if(!StringUtils.hasText(creatorDTO.getAccountNumber())){
            return false;
        }
        if(!StringUtils.hasText(creatorDTO.getBankName())){
            return false;
        }
        if(!StringUtils.hasText(creatorDTO.getCreatorNickName())){
            return false;
        }
        if(!StringUtils.hasText(creatorDTO.getBankBookImageUrl())){
            return false;
        }
        return true;
    }

    public FundingDTO tempFunding(String projectUrl,String email) {
        String manageUrl = UUID.randomUUID().toString().replaceAll("-", "");
        List<Creator> creatorList = creatorRepository.findByEmail(email);
        if (creatorList.isEmpty()) {
            throw new IllegalArgumentException("크리에이터가 없습니다.");
        }
        Creator findCreator = creatorList.get(0);
        Funding tempFunding = Funding.builder()
                .creator(findCreator)
                .postDate(LocalDateTime.now())
                .projectUrl(projectUrl)
                .manageUrl(manageUrl)
                .status(Status.editing)
                .build();
        findCreator.getFundingList().add(tempFunding);
        fundingRepository.save(tempFunding);

        FundingDTO fundingDTO = Utils.fundingEToD(tempFunding);
        return fundingDTO;
    }


    /**
     * 검증 절차 필요없어~~ 나중에 한번에 등록 했을때 검수신청
     */
    public FundingDTO tempThumbNail(ThumbNailDTO thumbNailDTO,String manageUrl) {

        Funding funding = getFunding(manageUrl);
        funding
                .thumbTitle(thumbNailDTO.getTitle())
                .thumbUrl(thumbNailDTO.getThumbNailUrl())
                .thumbCategory(thumbNailDTO.getCategory())
                .thumbTag(thumbNailDTO.getTag())
                .thumbSummary(thumbNailDTO.getSummary());

        FundingDTO fundingDTO = Utils.fundingEToD(funding);
        return fundingDTO;
    }

    public FundingDTO tempFundingPlan(FundingPlanDTO fundingPlanDTO,String manageUrl) {

        Funding funding = getFunding(manageUrl);
        funding
                .planGoalFundraising(fundingPlanDTO.getGoalFundraising())
                .planStartDate(fundingPlanDTO.getStartDate())
                .planEndDate(fundingPlanDTO.getEndDate())
                .planMinFundraising(fundingPlanDTO.getMinFundraising())
                .planMaxBacker(fundingPlanDTO.getMaxBacker());

        FundingDTO fundingDTO = Utils.fundingEToD(funding);
        return fundingDTO;
    }


    public FundingDTO tempDetail(DetailDTO detailDTO, String manageUrl) {

        Funding funding = getFunding(manageUrl);
        funding
                .detailContent(detailDTO.getContent())
                .detailBudget(detailDTO.getBudget())
                .detailSchedule(detailDTO.getSchedule())
                .detailAboutUs(detailDTO.getAboutUs());

        FundingDTO fundingDTO = Utils.fundingEToD(funding);
        return fundingDTO;
    }

    private Funding getFunding(String manageUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("manageUrl", manageUrl);
        if (fundingList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 펀딩입니다.");
        }
        return fundingList.get(0);
    }
}
