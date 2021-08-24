package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return new ResponseEntity<>(thumbNailList,HttpStatus.OK);
    }








}
