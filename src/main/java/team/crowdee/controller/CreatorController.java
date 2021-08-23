package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Funding;
import team.crowdee.domain.FundingStatus;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.CreatorDTO;
import team.crowdee.domain.dto.FundingIdDTO;
import team.crowdee.domain.dto.ThumbNailDTO;
import team.crowdee.domain.valuetype.AccountInfo;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.CreatorService;
import team.crowdee.service.FundingService;
import team.crowdee.service.MemberService;

import javax.mail.MessagingException;

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

    @PostMapping("/create")
    public ResponseEntity<?> createFunding(@RequestBody ThumbNailDTO thumbNailDTO) {
        Long funding_id = creatorService.createThumbNail(thumbNailDTO);
        if (funding_id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new FundingIdDTO(funding_id),HttpStatus.BAD_REQUEST);
    }
}
