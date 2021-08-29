package team.crowdee.util;


import team.crowdee.domain.*;
import team.crowdee.domain.dto.*;
import team.crowdee.domain.valuetype.AccountInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class Utils {

    public static String getTodayString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
//더미데이터5개 (크리에이터랑 펀딩) 만들기

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
        fundingDTO.setTotalBacker(funding.totalParticipant());
        fundingDTO.setStartDate(funding.getStartDate());
        fundingDTO.setEndDate(funding.getEndDate());
        fundingDTO.setVisitCount(funding.getVisitCount());
        fundingDTO.setTotalFundraising(funding.getTotalFundraising());
        fundingDTO.setStatus(funding.getStatus());
        return fundingDTO;
    }

    //backer
    public static BackerDTO backEToD(Member member) {
        BackerDTO backerDTO = new BackerDTO();
        backerDTO.setMemberId(member.getMemberId());
        backerDTO.setUserName(member.getUserName());
        backerDTO.setNickName(member.getNickName());
        backerDTO.setMobile(member.getMobile());
        backerDTO.setEmail(member.getEmail());
        backerDTO.setEmailCert(member.getEmailCert());
        backerDTO.setRegistDate(member.getRegistDate());
        backerDTO.setSecessionDate(member.getSecessionDate());
      //  backerDTO.setUserState(member.getUserState());
        backerDTO.setAuthorities(member.getAuthorities());

        return backerDTO;
    }

    public static CreatorDTO findAllCreator(Creator creator) {
        CreatorDTO creatorDTO = new CreatorDTO();
        Status status = creator.getStatus();
        String statuses = status.toString();
        creatorDTO.setCreatorId(creator.getCreatorId());
        creatorDTO.setCreatorNickName(creator.getCreatorNickName());
        creatorDTO.setBankBookImageUrl(creator.getAccountInfo().getBankBookImageUrl());
        creatorDTO.setMemberId(creator.getMember().getMemberId());
        creatorDTO.setAccountNumber(creator.getAccountInfo().getAccountNumber());
        creatorDTO.setBankName(creator.getAccountInfo().getBankName());
        creatorDTO.setBusinessNumber(creator.getBusinessNumber());
        creatorDTO.setStatus(statuses);
        return creatorDTO;
    }

    //creator
    public static CreatorBackDTO creatorBackEToD(Creator creator, Member member, AccountInfo accountInfo) {
        CreatorBackDTO creatorBackDTO = new CreatorBackDTO();
        creatorBackDTO.setMemberId(member.getMemberId());
        creatorBackDTO.setUserName(member.getUserName());
        creatorBackDTO.setNickName(member.getNickName());
        creatorBackDTO.setMobile(member.getMobile());
        creatorBackDTO.setEmail(member.getEmail());
        creatorBackDTO.setEmailCert(member.getEmailCert());
        creatorBackDTO.setRegistDate(member.getRegistDate());
        creatorBackDTO.setSecessionDate(member.getSecessionDate());
       // creatorBackDTO.setUserState(member.getUserState());
        creatorBackDTO.setAuthorities(member.getAuthorities());
        creatorBackDTO.setCreatorNickName(creator.getCreatorNickName());
        creatorBackDTO.setBusinessNumber(creator.getBusinessNumber());
        creatorBackDTO.setAccountNumber(accountInfo.getAccountNumber());
        creatorBackDTO.setBankName(accountInfo.getBankName());
        creatorBackDTO.setBankBookImageUrl(accountInfo.getBankBookImageUrl());

        return creatorBackDTO;
    }

    //전체회원목록
    public static AllMemberDTO allMemberEToD(Member member) {
        AllMemberDTO allMemberDTO = new AllMemberDTO();
        allMemberDTO.setMemberId(member.getMemberId());
        allMemberDTO.setUserName(member.getUserName());
        allMemberDTO.setEmail(member.getEmail());
        allMemberDTO.setRegistDate(member.getRegistDate());
        allMemberDTO.setAuthorities(member.getAuthorities());
        //멤버에 status추가필요 allMemberDTO.setStatus(member.getStatus());


        return allMemberDTO;
    }

    public static InspectionDTO inspectionFunding(Funding funding) {
        InspectionDTO inspectionDTO = new InspectionDTO();
        Long creatorId = funding.getCreator().getCreatorId();
        Status status = funding.getStatus();
        String inspection = status.toString();
        inspectionDTO.setCategory(funding.getCategory());
        inspectionDTO.setStatus(inspection);
        inspectionDTO.setStartDate(funding.getStartDate());
        inspectionDTO.setEndDate(funding.getEndDate());
        inspectionDTO.setTitle(funding.getTitle());
        inspectionDTO.setSummary(funding.getSummary());
        inspectionDTO.setCreatorId(creatorId);
        inspectionDTO.setFundingId(funding.getFundingId());
        return inspectionDTO;
    }

    public static MemberDTO inspectionMember(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        Set<Authority> authorities = member.getAuthorities();
        for (Authority authority : authorities) {
            String authorityName = authority.getAuthorityName();
        }

        memberDTO.setMemberId(member.getMemberId());
        memberDTO.setUserName(member.getUserName());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setRegistDate(member.getRegistDate());
        memberDTO.setAuthorities(authorities);
        return memberDTO;
    }


}