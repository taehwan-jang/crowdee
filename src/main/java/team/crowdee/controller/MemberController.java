package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.FindMailDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MailDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;
import team.crowdee.util.SendEmailService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final SendEmailService sendEmailService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원가입
    @PostMapping("/signUp")
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
                .registDate(LocalDateTime.now())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .build();

        Member memberJoin = memberService.join(member);

        return new ResponseEntity<>(memberJoin, HttpStatus.OK);

    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Member member = memberService.memberLogin(loginDTO);

        if(member == null) {
            //실패 : 멤버가 없기 때문에 예외
            return new ResponseEntity<>(member, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    //비밀번호 찾기/변경
    @PostMapping("/findPass")
    public ResponseEntity<?> lostPassword(@RequestBody FindMailDTO findMailDTO) {
        List<Member> findMember = memberRepository
                .findByEmailAndUserId(findMailDTO.getUserId(), findMailDTO.getEmail());
        if (findMember.isEmpty()) {
            return new ResponseEntity<>("아이디와 이메일을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName());
        sendEmailService.sendMail(mailDTO);
        return new ResponseEntity<>("이메일 발송되었습니다.", HttpStatus.OK);
    }
}