package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.service.FundingService;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/contents")
@RequiredArgsConstructor
@CrossOrigin
public class FundingController {

    private final FundingService fundingService;

    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        Map<String, List<ThumbNailDTO>> thumbNail = fundingService.findThumbNail();
        return new ResponseEntity<>(thumbNail, HttpStatus.OK);
    }

    /**
     * map("new",ThumbNailDTO) 최신펀딩 가져오기(시작일기준)
     */

    /**
     * map("under",ThumbNailDTO) 달성직전 가져오기(참여율 기준으로 100미만인 펀딩)
     */

    /**
     * map("over",ThumbNailDTO) 달성직전 가져오기(참여율 기준으로 100초과인 펀딩 & maxBacker 여유)
     */

    /**
     * map("popular",ThumbNailDTO) 관심도순 가져오기(조회수)
     */

    /**
     * 검색 가져오기
     */




    @GetMapping("/{projectUrl}")
    public ResponseEntity<?> showFundingDetail(@PathVariable String projectUrl) {
        FundingDTO fundingDTO = fundingService.findOneFunding(projectUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO, HttpStatus.OK);
    }
    /**
     * 찜하기 로직
     */


    /**
     * 참여하기 로직
     */



}
