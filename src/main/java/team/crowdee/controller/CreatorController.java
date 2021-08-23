package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.dto.*;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.CreatorService;
import team.crowdee.service.FundingService;
import team.crowdee.service.MemberService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/creator")
@Slf4j
public class CreatorController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CreatorService creatorService;
    private final FundingService fundingService;

    //크리에이터 등록
    @PostMapping("/signCreator")
    public ResponseEntity<?> creatorMember(@RequestBody CreatorDTO creatorDTO) {
        Creator joinCreator = creatorService.joinCreator(creatorDTO);

        if(joinCreator == null) {
            return new ResponseEntity<>("크리에이터 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("크리에이터 등록에 성공했습니다.", HttpStatus.OK);
    }

    /**
     * 최초 펀딩 작성시 단계별로 Entity 생성
     * ThumbNail -> Schedule -> Detail -> Funding
     */
    @PostMapping("/create/thumbNail")
    public ResponseEntity<?> createTempThumbNail(@RequestBody ThumbNailDTO thumbNailDTO) {
        Long fundingId = creatorService.tempThumbNail(thumbNailDTO);
        if (fundingId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new FundingIdDTO(fundingId),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create/fundingPlan")
    public ResponseEntity<?> createTempFundingPlan(@RequestBody FundingPlanDTO fundingPlanDTO) {
        Long fundingId = creatorService.tempFundingPlan(fundingPlanDTO);
        if (fundingId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new FundingIdDTO(fundingId),HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/create/detail")
    public ResponseEntity<?> createTempDetail(@RequestBody DetailDTO detailDTO) {
        Long fundingId = creatorService.tempDetail(detailDTO);
        if (fundingId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new FundingIdDTO(fundingId),HttpStatus.BAD_REQUEST);
    }



}
