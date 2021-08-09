package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;

@RestController
@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원가입
    @PostMapping("/member/regist")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) {

        Address address = new Address();
        address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());

        Member member = Member.builder()
                .userName(memberDTO.getUserName())
                .nickName(memberDTO.getNickName())
                .gender(memberDTO.getGender())
                .age(memberDTO.getAge())
                .birth(memberDTO.getBirth())
                .phone(memberDTO.getPhone())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .build();

        Member memberJoin = memberService.join(member);

        return new ResponseEntity<>(memberJoin, HttpStatus.OK);

    }

    //로그인
    @PostMapping("/member/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        Member member = memberService.memberLogin(loginDTO);

        if(member == null) {
            //실패 : 멤버가 없기 때문에 예외
            return new ResponseEntity<>(member, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(member, HttpStatus.OK);

    }
}