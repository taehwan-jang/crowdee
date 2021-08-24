package team.crowdee.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    /**
     * admin 관련 로직
     */
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @GetMapping("/findMemberAll")
    public ResponseEntity<?> allData() throws MessagingException {
        List<Member> members = memberRepository.findAll();
        if (members.isEmpty() && members!=null) {
            return new ResponseEntity<>("회원조회가 완료되었습니다.", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("회원조회실패", HttpStatus.BAD_REQUEST);
        }

    }
}
