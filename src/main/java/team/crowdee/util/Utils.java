package team.crowdee.util;


import team.crowdee.domain.Funding;
import team.crowdee.domain.dto.FundingDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String getTodayString() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static FundingDTO fundingEToD(Funding entity) {
        FundingDTO DTO = new FundingDTO();
        DTO.setFundingId(entity.getFundingId());
        DTO.setProjectUrl(entity.getProjectUrl());
        DTO.setManageUrl(entity.getManageUrl());
        DTO.setThumbNailUrl(entity.getThumbNailUrl());
        DTO.setTitle(entity.getTitle());
        DTO.setSummary(entity.getSummary());
        DTO.setCategory(entity.getCategory());
        DTO.setTag(entity.getTag());
        DTO.setContent(entity.getContent());
        DTO.setBudget(entity.getBudget());
        DTO.setSchedule(entity.getSchedule());
        DTO.setAboutUs(entity.getAboutUs());
        DTO.setPostDate(entity.getPostDate());
        DTO.setGoalFundraising(entity.getGoalFundraising());
        DTO.setMinFundraising(entity.getMinFundraising());
        DTO.setMaxBacker(entity.getMaxBacker());
        DTO.setStartDate(entity.getStartDate());
        DTO.setEndDate(entity.getEndDate());
        DTO.setVisitCount(entity.getVisitCount());
        DTO.setLikeCount(entity.getLikeCount());
        DTO.setTotalFundraising(entity.getTotalFundraising());
        DTO.setStatus(entity.getStatus());
        return DTO;
    }

}
