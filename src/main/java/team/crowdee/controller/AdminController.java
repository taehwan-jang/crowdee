package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import team.crowdee.customAnnotation.AdminAuth;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.security.CustomTokenProvider;
import team.crowdee.security.JwtFilter;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.AdminService;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    private final MemberRepository memberRepository;
    private final CustomTokenProvider customTokenProvider;

    //어드민 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        Member member = memberRepository.findAdmin(loginDTO.getEmail());
        Set<Authority> authorities = member.getAuthorities();
        for (Authority authority : authorities) {
            if(!(StringUtils.hasText(authority.getAuthorityName())&&authority.getAuthorityName().equals("admin"))) {
                return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
            }
        }
        String jwt = customTokenProvider.getToken(loginDTO);
        log.info("관리자 로그인시 토큰발급여부 ={}",jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        return new ResponseEntity<>(new AdminTokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }

    //backer 전체조회
    @GetMapping("/backer")
    public ResponseEntity<?> backer() {
        List<BackerAllDTO> backerAllDTO = adminService.backerAll();
        if (backerAllDTO == null) {
            return new ResponseEntity<>("backer 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backerAllDTO, HttpStatus.OK);
    }

    //creator 전체조회
    @GetMapping("/creator")
    @AdminAuth
    public ResponseEntity<?> creator() {
        List<CreatorAllDTO> creatorAllDTO = adminService.creatorAll();
        if (creatorAllDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creatorAllDTO, HttpStatus.OK);
    }

    //백커 상세조회
    @GetMapping("/backerView/{memberId}")
    @AdminAuth
    public ResponseEntity<?> backerView(@PathVariable("memberId") Long backerId) { //주소에 파라미터값 추가
        BackerViewDTO backerDTO = adminService.oneBacker(backerId);
        if (backerDTO == null) {
            return new ResponseEntity<>("백커 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backerDTO, HttpStatus.OK);
    }

    //크리에이터 상세조회
    @GetMapping("/creatorView/{createId}")
    @AdminAuth
    public ResponseEntity<?> creatorView(@PathVariable("createId") Long createId) { //주소에 파라미터값 추가
        CreatorViewDTO creatorViewDTO = adminService.oneCreator(createId);
        if (creatorViewDTO == null) {
            return new ResponseEntity<>("크리에이터 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creatorViewDTO, HttpStatus.OK);
    }

    //심사중 전체조회
    @GetMapping("/inspection")
    @AdminAuth
    public ResponseEntity<?> inspection() {
        List<CreatorViewDTO> inspectionDTO = adminService.inspectionAll();

        if (inspectionDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(inspectionDTO, HttpStatus.OK);
    }

    //크리에이터심사 승인
    @GetMapping("/creatorOK/{creatorId}")
    @AdminAuth
    public ResponseEntity<?> changeConfirm(@PathVariable("creatorId") Long creatorId) {
        Member member = adminService.confirmChange(creatorId);

        if (member == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 완료되었습니다.", HttpStatus.OK);
    }

    //크리에이터심사 거절(프론트랑같이테스트성공하면 완료)
    @PostMapping("/creatorNo/{creatorId}")
    @AdminAuth
    public ResponseEntity<?> changeReject(@PathVariable("creatorId") Long creatorId,
                                          @RequestBody RejectionDTO rejectionDTO) throws MessagingException {
        Member member = adminService.rejectCreator(creatorId,rejectionDTO);
        if (member == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 거절되었습니다.", HttpStatus.OK);
    }

    //펀딩전체조회
    @GetMapping("/funding")
    @AdminAuth
    public ResponseEntity<?> funding() {
        List<FundingAllDTO> fundingAllDTO = adminService.fundingAll();
        if (fundingAllDTO == null) {
            return new ResponseEntity<>("funding 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingAllDTO, HttpStatus.OK);
    }

    //펀딩 심사중 전체조회
    @GetMapping("/fundingInspection")
    @AdminAuth
    public ResponseEntity<?> fundingInspection() {
        List<FundingAllDTO> inspectionDTO = adminService.inspectionFunding();
        if (inspectionDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(inspectionDTO, HttpStatus.OK);
    }

    //펀딩 승인
    @GetMapping("/fundingOk/{fundingId}")
    @AdminAuth
    public ResponseEntity<?> fundingConfirm(@PathVariable("fundingId") Long fundingId) {
        Funding funding = adminService.confirmFunding(fundingId);

        if (funding == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 승인 완료되었습니다.", HttpStatus.OK);
    }

    //펀딩 거절
    @PostMapping("/FundingNo/{fundingId}")
    @AdminAuth
    public ResponseEntity<?> fundingReject(@PathVariable("fundingId") Long fundingId, @RequestBody RejectionDTO rejectionDTO) throws MessagingException {
        Funding funding = adminService.rejectFunding(fundingId, rejectionDTO);

        if (funding == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 거절 완료되었습니다.", HttpStatus.OK);
    }

}