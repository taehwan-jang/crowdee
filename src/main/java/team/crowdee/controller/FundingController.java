package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.service.FundingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("contents")
@RequiredArgsConstructor
@CrossOrigin
public class FundingController {

    private final FundingService fundingService;

    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        List<List<ThumbNailDTO>> thumbNail = fundingService.findThumbNail();
        return new ResponseEntity<>(thumbNail, HttpStatus.OK);
    }

    @GetMapping("/{projectUrl}")
    public ResponseEntity<?> showFundingDetail(@PathVariable String projectUrl) {
        FundingDTO fundingDTO = fundingService.findOneFunding(projectUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO, HttpStatus.OK);
    }

    @PostMapping("/searchTag")
    public ResponseEntity<?> searchTag(@RequestBody String tag) {
        List<ThumbNailDTO> thumbNailDTO = fundingService.findTag(tag);
        if (thumbNailDTO.isEmpty()) {
            return new ResponseEntity<>("검색결과가 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNailDTO, HttpStatus.OK);
    }

    /**
     * 검색 가져오기
     */


    /**
     * 찜하기 로직
     */


    /**
     * 참여하기 로직
     */



}
