package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.jwt.JwtFilter;
import team.crowdee.jwt.TokenProvider;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.SendEmailService;
import javax.mail.MessagingException;
import java.util.*;

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
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("/emailCert")
    public ResponseEntity<?> emailCert(@RequestParam String email) throws MessagingException {
        List<Member> findMember = memberRepository.findByParam("email", email);
        if (!findMember.isEmpty()) {
            return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }
        String authKey = mimeEmailService.sendAuthMail(email);
        Map<String, String> key = new HashMap<>();
        key.put("authKey", authKey);
        return new ResponseEntity<>(key, HttpStatus.OK);

    }

    //회원가입 추가할내용:회원탈퇴시에 회원존속여부 set해야함
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) throws MessagingException {
        Long member = memberService.join(memberDTO);
        if (member == null) {
            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("회원가입이완료되었습니다.", HttpStatus.CREATED);
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
        Member loginMember = memberService.memberLogin(loginDTO);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        if (loginMember == null) {
            //실패 : 멤버가 없기 때문에 예외
            return new ResponseEntity<>("아이디 패스워드를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
        }
        if(loginMember.getSecessionDate()==null) {
            boolean isSecession = StringUtils.hasText(loginMember.getSecessionDate());
            if(isSecession){
                return new ResponseEntity<>("탈퇴한 회원입니다.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }

    //비밀번호 찾기
    @PostMapping("/findPass")
    public ResponseEntity<?> lostPassword(@RequestBody FindMailDTO findMailDTO) {
        List<Member> findMember = memberService.findPassword(findMailDTO);
        if (findMember == null) {
            return new ResponseEntity<>("아이디와 이메일을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName(), member.getMemberId());
        if(mailDTO==null) {
            return new ResponseEntity<>("비밀번호 변경에 실패했습니다.",HttpStatus.BAD_REQUEST);
        }
        sendEmailService.sendMail(mailDTO);
        return new ResponseEntity<>("이메일 발송되었습니다.", HttpStatus.OK);
    }

    //태환오빠 코드
        /*List<Member> findMember = memberRepository
                .findByEmailAndUserId(findMailDTO.getUserId(), findMailDTO.getEmail());
        if (findMember.isEmpty()) {
            return new ResponseEntity<>("아이디와 이메일을 다시 확인해주세요", HttpStatus.BAD_REQUEST);
        }
        Member member = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName(), member.getMemberId());
        sendEmailService.sendMail(mailDTO);
        return new ResponseEntity<>("이메일 발송되었습니다.", HttpStatus.OK);
    }
        */

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

    //회원탈퇴
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody MemberDTO memberDTO) {
        Member member = memberService.deleteMember(memberDTO);
        if(member == null){
            return new ResponseEntity<>("탈퇴에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("탈퇴 되었습니다", HttpStatus.OK);
    }
}