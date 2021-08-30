package team.crowdee.util;

import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String getTodayString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
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

    //펀딩상세조회로예상...
    public static FundingDTO fundingEToD(Funding funding) {
        FundingDTO fundingDTO = new FundingDTO();
        fundingDTO.setFundingId(funding.getFundingId());
        fundingDTO.setProjectUrl(funding.getProjectUrl());
        fundingDTO.setManageUrl(funding.getManageUrl());
        fundingDTO.setThumbNailUrl(funding.getThumbNailUrl());
        fundingDTO.setTitle(funding.getTitle());
        fundingDTO.setSummary(funding.getSummary());
        fundingDTO.setCategory(funding.getCategory());
        fundingDTO.setTag(funding.getTag());
        fundingDTO.setContent(funding.getContent());
        fundingDTO.setBudget(funding.getBudget());
        fundingDTO.setSchedule(funding.getSchedule());
        fundingDTO.setAboutUs(funding.getAboutUs());
        fundingDTO.setPostDate(funding.getPostDate());
        fundingDTO.setGoalFundraising(funding.getGoalFundraising());
        fundingDTO.setMinFundraising(funding.getMinFundraising());
        fundingDTO.setMaxBacker(funding.getMaxBacker());
        fundingDTO.setStartDate(funding.getStartDate());
        fundingDTO.setEndDate(funding.getEndDate());
        fundingDTO.setVisitCount(funding.getVisitCount());
        fundingDTO.setLikeCount(funding.getLikeCount());
        fundingDTO.setTotalFundraising(funding.getTotalFundraising());
        fundingDTO.setStatus(funding.getStatus());
        return fundingDTO;
    }
}