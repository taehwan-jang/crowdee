package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.crowdee.customAnnotation.MemberAuth;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.security.CustomJWTFilter;
import team.crowdee.security.CustomTokenProvider;
import team.crowdee.security.JwtFilter;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;
import team.crowdee.service.OrderService;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.SendEmailService;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
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
    private final OrderService orderService;
    private final MemberRepository memberRepository;
    private final CustomTokenProvider customTokenProvider;
    private final CustomJWTFilter customJWTFilter;

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
    //회원가입
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody MemberDTO memberDTO) throws MessagingException {
        Long member = memberService.join(memberDTO);
        if (member == null) {
            return new ResponseEntity<>("회원가입에 실패했습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("회원가입이완료되었습니다.", HttpStatus.CREATED);
    }
    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        String jwt = customTokenProvider.getToken(loginDTO);
        String userNickName = memberService.getUserNickName(loginDTO.getEmail());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        return new ResponseEntity<>(new TokenDTO(jwt,userNickName), httpHeaders, HttpStatus.OK);
    }

    //비밀번호 찾기
    @PostMapping("/findPass")
    public ResponseEntity<?> lostPassword(@RequestBody FindMailDTO findMailDTO) {
        List<Member> findMember = memberService.findPassword(findMailDTO);
        if (findMember.isEmpty()) {
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


    //비밀번호 수정
    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestBody ChangePassDTO changePassDTO) {
        Member member = memberService.memberChangePass(changePassDTO);
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
            return new ResponseEntity<>("중복된 닉네임 입니다.", HttpStatus.BAD_REQUEST);
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

    //=========마이페이지=============//
    //멤버소개 -> 필요없음
    @GetMapping("/myPage/fundingList")
    @MemberAuth
    public ResponseEntity<?> myFundingHistory(HttpServletRequest request) {
        String email = customJWTFilter.findEmail(request);
        List<ThumbNailDTO> thumbNail = memberService.fundingHistory(email);
        if (thumbNail.isEmpty()) {
            return new ResponseEntity<>("후원 펀딩이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNail, HttpStatus.OK);
    }

    @GetMapping("/myPage/wishList")
    @MemberAuth
    public ResponseEntity<?> myWishList(HttpServletRequest request) {
        String email = customJWTFilter.findEmail(request);
        List<ThumbNailDTO> thumbNail = memberService.wishFunding(email);
        if (thumbNail.isEmpty()) {
            return new ResponseEntity<>("후원 펀딩이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(thumbNail, HttpStatus.OK);
    }

    @GetMapping("/myPage/waitingForPayment")
    @MemberAuth
    public ResponseEntity<?> waitingPaymentList(HttpServletRequest request) {
        String email = customJWTFilter.findEmail(request);
        List<WaitingPaymentDTO> paymentDTOList = orderService.listUpWaitingPayment(email);
        if (paymentDTOList.isEmpty()) {
            return new ResponseEntity<>("참여 내역이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(paymentDTOList,HttpStatus.OK);
    }


}