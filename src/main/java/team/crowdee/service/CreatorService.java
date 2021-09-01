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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            throw new IllegalArgumentException("양식을 모두 채워주세요");
        }
        Member member = memberRepository.findById(creatorDTO.getMemberId());
        AccountInfo account = new AccountInfo();
        account.setAccountNumber(creatorDTO.getAccountNumber());
        account.setBankBookImageUrl(creatorDTO.getBankBookImageUrl());
        account.setBankName(creatorDTO.getBankName());
        Creator creator = Creator.builder()
                .aboutMe(creatorDTO.getAboutMe())
                .career(creatorDTO.getCareer())
                .profileImgUrl(creatorDTO.getProfileImgUrl())
                .status(Status.inspection)
                .creatorNickName(creatorDTO.getCreatorNickName())
                .BusinessNumber(creatorDTO.getBusinessNumber())
                .accountInfo(account)
                .build();
        creatorRepository.save(creator);
        member.joinCreator(creator);
        return creator;
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

    public FundingViewDTO tempFunding(String projectUrl, String email) {
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
                .result(false)
                .build();
        findCreator.getFundingList().add(tempFunding);
        fundingRepository.save(tempFunding);

        FundingViewDTO fundingViewDTO = Utils.fundingEToD(tempFunding);
        return fundingViewDTO;
    }


    /**
     * 검증 절차는 최종 심사단계에서 진행
     * --transaction 단위 별 메서드 정의 되어있는데
     * 2개 각각의 트랜잭션이 적용되어있다면
     * 영속상태의 객체가 넘어갈까?
     */
    public FundingViewDTO tempThumbNail(ThumbNailDTO thumbNailDTO, String manageUrl) {

        Funding funding = getFunding(manageUrl);

        funding
                .thumbTitle(thumbNailDTO.getTitle())
                .thumbSubTitle(thumbNailDTO.getSubTitle())
                .thumbUrl(thumbNailDTO.getThumbNailUrl())
                .thumbCategory(thumbNailDTO.getCategory())
                .thumbTag(thumbNailDTO.getTag())
                .thumbSummary(thumbNailDTO.getSummary());

        FundingViewDTO fundingViewDTO = Utils.fundingEToD(funding);
        return fundingViewDTO;
    }

    public FundingViewDTO tempFundingPlan(FundingPlanDTO fundingPlanDTO, String manageUrl) {

        Funding funding = getFunding(manageUrl);
        funding
                .planGoalFundraising(fundingPlanDTO.getGoalFundraising())
                .planStartDate(fundingPlanDTO.getStartDate())
                .planEndDate(fundingPlanDTO.getEndDate())
                .planMinFundraising(fundingPlanDTO.getMinFundraising())
                .planMaxBacker(fundingPlanDTO.getMaxBacker())
                .planRestTicket(fundingPlanDTO.getMaxBacker());

        FundingViewDTO fundingViewDTO = Utils.fundingEToD(funding);
        return fundingViewDTO;
    }


    public FundingViewDTO tempDetail(DetailDTO detailDTO, String manageUrl) {

        Funding funding = getFunding(manageUrl);
        funding
                .detailContent(detailDTO.getContent())
                .detailBudget(detailDTO.getBudget())
                .detailSchedule(detailDTO.getSchedule())
                .detailAboutUs(detailDTO.getAboutUs());

        FundingViewDTO fundingViewDTO = Utils.fundingEToD(funding);
        return fundingViewDTO;
    }

    @Transactional(readOnly = true)
    public FundingViewDTO showPreview(String manageUrl) {
        Funding funding = getFunding(manageUrl);
        Creator creator = funding.getCreator();
        List<Funding> creatorFundingList = fundingRepository.findByCreatorForPreview(creator.getCreatorId());
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

    private Funding getFunding(String manageUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("manageUrl", manageUrl);
        if (fundingList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 펀딩입니다.");
        }
        return fundingList.get(0);
    }

    public FundingViewDTO changeStatus(String manageUrl) {
        List<Funding> fundingList = fundingRepository.findByParam("manageUrl", manageUrl);
        if (fundingList.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 펀딩입니다.");
        }
        Funding funding = fundingList.get(0);
        log.warn("status 바뀌니???");
        funding.changeStatus(Status.inspection);
        log.warn("status 바뀌니???2={}",funding.getStatus());
        FundingViewDTO fundingViewDTO = Utils.fundingEToD(funding);
        return fundingViewDTO;
    }

    @Transactional(readOnly = true)
    public List<EditingListDTO> findEditingList(String email) {

        List<Creator> creatorList = creatorRepository.findByEmail(email);
        if (creatorList.isEmpty()) {
            throw new IllegalArgumentException("크리에이터가 아닙니다.");
        }
        List<EditingListDTO> editingList = new ArrayList<>();
        Creator creator = creatorList.get(0);
        List<Funding> fundingList = fundingRepository.findByCreatorForEditing(creator);
        if (!fundingList.isEmpty()) {
            for (Funding funding : fundingList) {
                editingList.add(new EditingListDTO(
                        funding.getTitle(),
                        funding.getPostDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        funding.getManageUrl()));
            }
            return editingList;
        }
        return null;
    }
}
