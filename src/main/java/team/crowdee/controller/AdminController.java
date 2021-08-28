package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.crowdee.domain.dto.AllMemberDTO;
import team.crowdee.domain.dto.BackerDTO;
import team.crowdee.domain.dto.CreatorBackDTO;
import team.crowdee.service.AdminService;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    /**
     * admin 관련 로직
     */
    private final AdminService adminService;

    //백커 상세조회
    @GetMapping("/backerView/{memberId}")
    public ResponseEntity<?> backerView(@PathVariable("memberId") Long backerId) { //주소에 파라미터값 추가
        BackerDTO backerDTO = adminService.oneBacker(backerId);
        System.out.println("과연?" + backerDTO.getNickName());
        if (backerDTO == null) {
            return new ResponseEntity<>("백커 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(backerDTO, HttpStatus.OK);
    }

    //크리에이터 상세조회
    @GetMapping("/creatorView/{createId}")
    public ResponseEntity<?> creatorView(@PathVariable("createId") Long createId) { //주소에 파라미터값 추가
        CreatorBackDTO creatorBackDTO = adminService.oneCreator(createId);
        if (creatorBackDTO == null) {
            return new ResponseEntity<>("크리에이터 상세보기 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(creatorBackDTO, HttpStatus.OK);
    }

    //전체회원조회

//    @PostMapping("/findMemberAll")
//    public ResponseEntity<?> allMember() {
//        List<AllMemberDTO> allMemberDTO = adminService.AllMember();
//        if(allMemberDTO == null) {
//            return new ResponseEntity<>("회원정보 전체 조회 실패", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(allMemberDTO, HttpStatus.OK);
//    }

    //전체회원조회

    @GetMapping("/findMemberAll")
    public ResponseEntity<?> allMember() {
        List<AllMemberDTO> allMemberDTO = adminService.AllMember();
        if(allMemberDTO == null) {
            return new ResponseEntity<>("회원정보 전체 조회 실패", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(allMemberDTO, HttpStatus.OK);
    }

}