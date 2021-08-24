package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.dto.*;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.CreatorService;
import team.crowdee.service.FundingService;
import team.crowdee.service.MemberService;

import java.util.UUID;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/creator")
@Slf4j
public class CreatorController {

    private final CreatorService creatorService;

    //크리에이터 등록
    @PostMapping("/signCreator")
    public ResponseEntity<?> creatorMember(@RequestBody CreatorDTO creatorDTO) {
        Creator joinCreator = creatorService.joinCreator(creatorDTO);

        if(joinCreator == null) {
            return new ResponseEntity<>("크리에이터 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("크리에이터 등록에 성공했습니다.", HttpStatus.OK);
    }

    @GetMapping("/create/funding/{projectUrl}/{id}")
    public ResponseEntity<?> createFunding(@PathVariable String projectUrl,@PathVariable Long id) {
        //토큰으로부터 멤버를 꺼내와서 creatorId 를 받아온다? ㅇㅇ 받아오는편이 좋을듯 먼저 확인 후 일단 클라에서 전송받는다 가정
        FundingDTO tempFundingDTO = creatorService.tempFunding(projectUrl,id);
        if (tempFundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tempFundingDTO, HttpStatus.OK);
    }

    /**
     * 최초 펀딩 작성시 단계별로 Entity 생성
     * ThumbNail -> Schedule -> Detail -> Funding
     */
    @PostMapping("/create/thumbNail")
    public ResponseEntity<?> createTempThumbNail(@RequestBody ThumbNailDTO thumbNailDTO) {
        FundingDTO fundingDTO = creatorService.tempThumbNail(thumbNailDTO);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }



    @PostMapping("/create/fundingPlan")
    public ResponseEntity<?> createTempFundingPlan(@RequestBody FundingPlanDTO fundingPlanDTO) {
        FundingDTO fundingDTO = creatorService.tempFundingPlan(fundingPlanDTO);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }

    @PostMapping("/create/detail")
    public ResponseEntity<?> createTempDetail(@RequestBody DetailDTO detailDTO) {
        FundingDTO fundingDTO = creatorService.tempDetail(detailDTO);
        if (fundingDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingDTO,HttpStatus.OK);
    }



}
