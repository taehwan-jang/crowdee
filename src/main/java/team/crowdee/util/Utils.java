package team.crowdee.util;


import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import team.crowdee.domain.valuetype.AccountInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class Utils {

    public static String getTodayString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static FundingViewDTO fundingEToD(Funding funding, List<SimpleFundingListDTO> fundingList) {
        FundingViewDTO fundingViewDTO = new FundingViewDTO();
        fundingViewDTO.setFundingId(funding.getFundingId());
        fundingViewDTO.setProjectUrl(funding.getProjectUrl());
        fundingViewDTO.setManageUrl(funding.getManageUrl());
        fundingViewDTO.setThumbNailUrl(funding.getThumbNailUrl());
        fundingViewDTO.setTitle(funding.getTitle());
        fundingViewDTO.setSummary(funding.getSummary());
        fundingViewDTO.setCategory(funding.getCategory());
        fundingViewDTO.setTag(funding.getTag());
        fundingViewDTO.setContent(funding.getContent());
        fundingViewDTO.setBudget(funding.getBudget());
        fundingViewDTO.setSchedule(funding.getSchedule());
        fundingViewDTO.setAboutUs(funding.getAboutUs());
        fundingViewDTO.setGoalFundraising(funding.getGoalFundraising());
        fundingViewDTO.setMinFundraising(funding.getMinFundraising());
        fundingViewDTO.setMaxBacker(funding.getMaxBacker());
        fundingViewDTO.setTotalBacker(funding.totalParticipant());
        fundingViewDTO.setStartDate(funding.getStartDate());
        fundingViewDTO.setEndDate(funding.getEndDate());
        fundingViewDTO.setVisitCount(funding.getVisitCount());
        fundingViewDTO.setTotalFundraising(funding.getTotalFundraising());
        fundingViewDTO.setStatus(funding.getStatus());
        //========크리에이터 정보========//
        fundingViewDTO.setAboutMe(funding.getCreator().getAboutMe());
        fundingViewDTO.setCareer(funding.getCreator().getCareer());
        fundingViewDTO.setCreatorNickName(funding.getCreator().getCreatorNickName());
        fundingViewDTO.setProfileImgUrl(funding.getCreator().getProfileImgUrl());
        fundingViewDTO.setFundingList(fundingList);
        //=========찜한 사람들 목록========//
        List<Member> memberList = funding.getMemberList();
        StringBuffer memberEmailList = new StringBuffer();
        for (Member member : memberList) {
            memberEmailList.append(member.getEmail()+" ");
        }
        fundingViewDTO.setMemberList(memberEmailList.toString());


        return fundingViewDTO;
    }
    public static FundingViewDTO fundingEToD(Funding funding) {
        FundingViewDTO fundingViewDTO = new FundingViewDTO();
        fundingViewDTO.setFundingId(funding.getFundingId());
        fundingViewDTO.setProjectUrl(funding.getProjectUrl());
        fundingViewDTO.setManageUrl(funding.getManageUrl());
        fundingViewDTO.setThumbNailUrl(funding.getThumbNailUrl());
        fundingViewDTO.setTitle(funding.getTitle());
        fundingViewDTO.setSummary(funding.getSummary());
        fundingViewDTO.setCategory(funding.getCategory());
        fundingViewDTO.setTag(funding.getTag());
        fundingViewDTO.setContent(funding.getContent());
        fundingViewDTO.setBudget(funding.getBudget());
        fundingViewDTO.setSchedule(funding.getSchedule());
        fundingViewDTO.setAboutUs(funding.getAboutUs());
        fundingViewDTO.setGoalFundraising(funding.getGoalFundraising());
        fundingViewDTO.setMinFundraising(funding.getMinFundraising());
        fundingViewDTO.setMaxBacker(funding.getMaxBacker());
        fundingViewDTO.setTotalBacker(funding.totalParticipant());
        fundingViewDTO.setStartDate(funding.getStartDate());
        fundingViewDTO.setEndDate(funding.getEndDate());
        fundingViewDTO.setVisitCount(funding.getVisitCount());
        fundingViewDTO.setTotalFundraising(funding.getTotalFundraising());
        fundingViewDTO.setStatus(funding.getStatus());

        return fundingViewDTO;
    }

    //백커 전체조회
    public static BackerAllDTO allBackEToD(Member member) {

        BackerAllDTO backerAllDTO = new BackerAllDTO();
        backerAllDTO.setMemberId(member.getMemberId());
        backerAllDTO.setUserName(member.getUserName());
        backerAllDTO.setEmail(member.getEmail());
        backerAllDTO.setRegistDate(member.getRegistDate());
        backerAllDTO.setAuthorities("backer");
        backerAllDTO.setStatus(member.getStatus());


        return backerAllDTO;
    }

    //크리에이터 전체조회
    public static CreatorAllDTO allCreatorEToD(Creator creator) {

        CreatorAllDTO creatorAllDTO = new CreatorAllDTO();
        creatorAllDTO.setCreatorId(creator.getCreatorId());
        creatorAllDTO.setCreatorNickName(creator.getCreatorNickName());
        creatorAllDTO.setBusinessNumber(creator.getBusinessNumber());
        creatorAllDTO.setAccountNumber(creator.getAccountInfo().getAccountNumber());
        creatorAllDTO.setAuthorities("backer, creator");
        creatorAllDTO.setStatus(creator.getStatus());

        return creatorAllDTO;
    }

    //백커 상세보기
    public static BackerViewDTO backViewEToD(Member member) {

        BackerViewDTO backerViewDTO = new BackerViewDTO();
        backerViewDTO.setMemberId(member.getMemberId());
        backerViewDTO.setUserName(member.getUserName());
        backerViewDTO.setNickName(member.getNickName());
        backerViewDTO.setMobile(member.getMobile());
        backerViewDTO.setEmail(member.getEmail());
        backerViewDTO.setEmailCert(member.getEmailCert());
        backerViewDTO.setRegistDate(member.getRegistDate());
        backerViewDTO.setSecessionDate(member.getSecessionDate());
        backerViewDTO.setAuthorities("backer");
        backerViewDTO.setStatus(member.getStatus());

        return backerViewDTO;
    }

    //크리에이터 상세보기
    public static CreatorViewDTO creatorViewEToD(Creator creator) {
        CreatorViewDTO creatorViewDTO = new CreatorViewDTO();
        creatorViewDTO.setCreatorID(creator.getCreatorId());
        creatorViewDTO.setCreatorNickName(creator.getCreatorNickName());
        creatorViewDTO.setBusinessNumber(creator.getBusinessNumber());
        creatorViewDTO.setAccountNumber(creator.getAccountInfo().getAccountNumber());
        creatorViewDTO.setBankName(creator.getAccountInfo().getBankName());
        creatorViewDTO.setBankBookImageUrl(creator.getAccountInfo().getBankBookImageUrl());
        creatorViewDTO.setAuthorities("backer, creator");
        creatorViewDTO.setStatus(creator.getStatus());
        return creatorViewDTO;
    }

    //심사중 전체조회
    public static CreatorViewDTO inspectionEToD(Creator creator) {

        CreatorViewDTO creatorViewDTO = new CreatorViewDTO();
        creatorViewDTO.setCreatorID(creator.getCreatorId());
        creatorViewDTO.setCreatorNickName(creator.getCreatorNickName());
        creatorViewDTO.setBusinessNumber(creator.getBusinessNumber());
        creatorViewDTO.setAccountNumber(creator.getAccountInfo().getAccountNumber());
        creatorViewDTO.setBankName(creator.getAccountInfo().getBankName());
        creatorViewDTO.setBankBookImageUrl(creator.getAccountInfo().getBankBookImageUrl());
        creatorViewDTO.setStatus(creator.getStatus());

        return creatorViewDTO;
    }

    //펀딩 전체, 심사조회
    public static FundingAllDTO allFundingEToD(Funding funding) {

        FundingAllDTO fundingAllDTO = new FundingAllDTO();
        fundingAllDTO.setFundingId(funding.getFundingId());
        fundingAllDTO.setTitle(funding.getTitle());
        fundingAllDTO.setSummary(funding.getSummary());
        fundingAllDTO.setCategory(funding.getCategory());
        fundingAllDTO.setPostDate(funding.getPostDate());
        fundingAllDTO.setStatus(funding.getStatus());

        return fundingAllDTO;
    }

}