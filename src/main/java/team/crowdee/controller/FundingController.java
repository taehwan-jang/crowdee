package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Funding;
import team.crowdee.domain.FundingStatus;
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

    @PostMapping("/create")
    @PatchMapping("/createFunding/{id}")
    public ResponseEntity<?> createFunding(@RequestBody FundingStatus fundingStatus, @PathVariable Long id) {
        Funding findFunding = fundingService.changeFundingStatus(fundingStatus,id);
        return null;
    }

    @GetMapping
    public ResponseEntity<?> showAllThumbNail() {
        List<ThumbNailDTO> thumbNailList = fundingService.findThumbNail();
        return new ResponseEntity<>(thumbNailList,HttpStatus.OK);
    }




}
