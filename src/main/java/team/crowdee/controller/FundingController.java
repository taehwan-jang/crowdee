package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Funding;
import team.crowdee.domain.FundingStatus;
import team.crowdee.domain.ThumbNail;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.repository.FundingRepository;
import team.crowdee.service.FundingService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/content")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;



    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        List<ThumbNailDTO> thumbNailList = fundingService.findThumbNail();
        ThumbNailDTO thumbNailDTO = ThumbNailDTO.builder()
                .category("이건카테고리")
                .rateOfAchievement("이건진행률")
                .summery("이건요약정보")
                .restDate(10)
                .thumbNailUrl("https://cdn.notefolio.net/img/5a/af/5aaf36082b60a519aac5db918f67fabd809ee35def6cfd2020855da5e6565db0_v1.jpg")
                .title("이건제목")
                .goalFundraising(1000000)
                .funding_id(1L)
                .build();
        thumbNailList.add(thumbNailDTO);
        return new ResponseEntity<>(thumbNailList,HttpStatus.OK);
    }




}
