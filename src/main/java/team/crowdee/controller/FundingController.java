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

@RestController
@Slf4j
@RequestMapping("/contents")
@RequiredArgsConstructor
@CrossOrigin
public class FundingController {

    private final FundingService fundingService;

    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        List<ThumbNailDTO> thumbNailList = fundingService.findThumbNail();
        return new ResponseEntity<>(thumbNailList, HttpStatus.OK);
    }

    @GetMapping("/{projectUrl}")
    public ResponseEntity<?> showFundingDetail(@PathVariable String projectUrl) {
        FundingDTO fundingDTO = fundingService.findOneFunding(projectUrl);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO, HttpStatus.OK);
    }
}
