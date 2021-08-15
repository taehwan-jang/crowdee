package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.UserState;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.SendEmailService;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final SendEmailService sendEmailService;
    private final MimeEmailService mimeEmailService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //회원가입 추가할내용:회원탈퇴시에 회원존속여부 set해야함
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) throws MessagingException {
        Address address = new Address();
        address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());
        String authKey = mimeEmailService.sendAuthMail(memberDTO.getEmail());
        Member member = Member.builder()
                .userId(memberDTO.getUserId())
                .password(memberDTO.getPassword())
                .userName(memberDTO.getUserName())
                .nickName(memberDTO.getNickName())
                .gender(memberDTO.getGender())
                .age(memberDTO.getAge())
                .birth(memberDTO.getBirth())
                .phone(memberDTO.getPhone())
                .registDate(LocalDateTime.now())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .userState(UserState.guest)
                .emailCert(authKey)
                .build();

        Member memberJoin = memberService.join(member);
        if (memberJoin == null) {
            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(memberJoin, HttpStatus.CREATED);
    }

    @GetMapping("/signUpConfirm")
    public ResponseEntity<?> signUpConfirm(@RequestParam String email, @RequestParam String authKey) {
        Member member = memberService.signUpConfirm(email,authKey);
        if (member == null) {
            return new ResponseEntity<>("인증에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("인증이 완료되었습니다.", HttpStatus.OK);
    }


    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Member member = memberService.memberLogin(loginDTO);

        if (member == null) {
            //실패 : 멤버가 없기 때문에 예외
            return new ResponseEntity<>("아이디 패스워드를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
        boolean isSecession = StringUtils.hasText(member.getSecessionDate());

        if(isSecession){
            return new ResponseEntity<>("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    //비밀번호 찾기
    @PostMapping("/findPass")
    public ResponseEntity<?> lostPassword(@RequestBody FindMailDTO findMailDTO) {
        List<Member> findMember = memberRepository
                .findByEmailAndUserId(findMailDTO.getUserId(), findMailDTO.getEmail());
        if (findMember.isEmpty()) {
            return new ResponseEntity<>("아이디와 이메일을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName(), member.getMemberId());
        sendEmailService.sendMail(mailDTO);
        return new ResponseEntity<>("이메일 발송되었습니다.", HttpStatus.OK);
    }

    //비밀번호 수정
    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestBody ChangePassDTO changePassDTO) {
        Member member = memberService.memberChangPass(changePassDTO);
        if (member == null) {
            return new ResponseEntity<>("패스워드를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("패스워드가 수정되었습니다.", HttpStatus.OK);
    }

    //회원 정보 수정
    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.memberEdit(memberDTO);
        if(member == null ){
            return new ResponseEntity<>("닉네임 중복으로 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("정보가 수정되었습니다", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.deleteMember(memberDTO);
        if(member == null){
            return new ResponseEntity<>("탈퇴에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("탈퇴 되었습니다", HttpStatus.OK);
    }
}