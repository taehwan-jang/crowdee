package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;

import team.crowdee.jwt.CustomJWTFilter;
import team.crowdee.jwt.CustomTokenProvider;
import team.crowdee.jwt.JwtFilter;
import team.crowdee.service.AdminService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    /**
     * admin 관련 로직
     */
    private final AdminService adminService;
    private final CustomTokenProvider customTokenProvider;
    private final CustomJWTFilter customJWTFilter;

    //어드민 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletRequest request) {
        boolean admin = customJWTFilter.isAdmin(request);
        if (!admin) {
            return new ResponseEntity<>("잘못된 접근입니다.", HttpStatus.FORBIDDEN);
        }
        String adminLogin = adminService.login(loginDTO);
        String jwt = customTokenProvider.getToken(loginDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer" + jwt);

        return new ResponseEntity<>(new AdminTokenDTO(jwt, adminLogin), httpHeaders, HttpStatus.OK);
    }

    //백커 전체조회
    @GetMapping("/backer")
    public ResponseEntity<?> backer() {
        List<BackerAllDTO> backerAllDTO = adminService.backerAll();
        if (backerAllDTO == null) {
            return new ResponseEntity<>("backer 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backerAllDTO, HttpStatus.OK);
    }

    //크리에이터 전체조회
    @GetMapping("/creator")
    public ResponseEntity<?> creator() {
        List<CreatorAllDTO> creatorAllDTO = adminService.creatorAll();

        if (creatorAllDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creatorAllDTO, HttpStatus.OK);
    }

    //백커 상세조회
    @GetMapping("/backerView/{memberId}")
    public ResponseEntity<?> backerView(@PathVariable("memberId") Long backerId) { //주소에 파라미터값 추가
        BackerViewDTO backerDTO = adminService.oneBacker(backerId);

        if (backerDTO == null) {
            return new ResponseEntity<>("백커 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backerDTO, HttpStatus.OK);
    }

    //크리에이터 상세조회
    @GetMapping("/creatorView/{createId}")
    public ResponseEntity<?> creatorView(@PathVariable("createId") Long createId) { //주소에 파라미터값 추가
        CreatorViewDTO creatorViewDTO = adminService.oneCreator(createId);

        if (creatorViewDTO == null) {
            return new ResponseEntity<>("크리에이터 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creatorViewDTO, HttpStatus.OK);
    }

    //심사중 전체조회
    @GetMapping("/inspection")
    public ResponseEntity<?> inspection() {
        List<CreatorViewDTO> inspectionDTO = adminService.inspectionAll();

        if (inspectionDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(inspectionDTO, HttpStatus.OK);
    }

    //크리에이터심사 승인
    @GetMapping("/creatorOK/{creatorId}")
    public ResponseEntity<?> changeConfirm(@PathVariable("creatorId") Long creatorId) {
        Member member = adminService.confirmChange(creatorId);

        if (member == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 완료되었습니다.", HttpStatus.OK);
    }

    //크리에이터심사 거절(미완성)
    @GetMapping("/changeRefuse/{memberId}")
    public ResponseEntity<?> changeRefuse(@PathVariable("memberId") Long memberId) {
        adminService.refuseChange(memberId);
        return new ResponseEntity<>("심사 거절되었습니다.", HttpStatus.OK);
    }

    //펀딩전체조회
    @GetMapping("/funding")
    public ResponseEntity<?> funding() {
        List<FundingAllDTO> fundingAllDTO = adminService.fundingAll();
        if (fundingAllDTO == null) {
            return new ResponseEntity<>("funding 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(fundingAllDTO, HttpStatus.OK);
    }

    //펀딩 심사중 전체조회
    @GetMapping("/fundingInspection")
    public ResponseEntity<?> fundingInspection() {
        List<FundingAllDTO> inspectionDTO = adminService.inspectionFunding();

        if (inspectionDTO == null) {
            return new ResponseEntity<>("creator 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(inspectionDTO, HttpStatus.OK);
    }

    //펀딩 승인
    @GetMapping("/fundingOk/{fundingId}")
    public ResponseEntity<?> fundingConfirm(@PathVariable("fundingId") Long fundingId) {
        Funding funding = adminService.confirmFunding(fundingId);

        if (funding == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 승인 완료되었습니다.", HttpStatus.OK);
    }

    //펀딩 거절
    @GetMapping("/fundingNo/{fundingId}")
    public ResponseEntity<?> fundingReject(@PathVariable("fundingId") Long fundingId) {
        Funding funding = adminService.rejectFunding(fundingId);

        if (funding == null) {
            return new ResponseEntity<>("심사 불가 상태입니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("심사 거절 완료되었습니다.", HttpStatus.OK);
    }


}