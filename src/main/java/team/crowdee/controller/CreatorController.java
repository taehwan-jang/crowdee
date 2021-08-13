package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.CreatorDTO;
import team.crowdee.domain.valuetype.AccountInfo;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.CreatorService;
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

    //크리에이터 등록
    @PostMapping("/signCreator")
    public ResponseEntity<?> creatorMember(@RequestBody CreatorDTO creatorDTO) throws MessagingException {
        Creator joinCreator = creatorService.joinCreator(creatorDTO);

        if(joinCreator == null) {
            return new ResponseEntity<>("크리에이터 등록에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("크리에이터 등록에 성공했습니다.", HttpStatus.OK);
    }
}
