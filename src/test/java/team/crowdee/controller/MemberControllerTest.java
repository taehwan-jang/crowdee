package team.crowdee.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberControllerTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberController memberController;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void 크리에이터테스트() throws Exception {
        //given
        Member member5 = memberRepository.findById(5L);
        Creator creator1 = Creator.builder()
                .BusinessNumber("123-1231-1231")
                .build();
        memberRepository.saveCreator(creator1);
        member5.joinCreator(creator1);
        System.out.println(member5.getUserName());
        em.flush();
        em.clear();
        //when
        Member findMember = memberRepository.findById(5L);
        Creator findCreator = findMember.getCreator();
        //then
        System.out.println("findCreator.getBusinessNumber() = " + findCreator.getBusinessNumber());
        assertThat(findCreator).isEqualTo(creator1);
    }

    @Test
    @Rollback(value = false)
    public void 더미데이터() {
        for (int i = 0; i < 10; i++) {
            memberRepository.save(
                Member.builder()
                    .birth("1992/" + i)
                    .nickName("테스트" + i)
                    .email("mail" + i + "@mail.com")
                    .userName("user" + i)
                    .registDate(LocalDateTime.now())
                    .age(20 + i)
                    .userId("testId" + i)
                    .gender("남자")
                    .mobile("010-1234-123" + i)
                    .build()
            );
        }
    }

    @Test  //session 데이터없으면 널포인트뜨니까 넣고 테스트돌리세용!!
    public void 테스트_로그인(){
       LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("testId0");
        loginDTO.setPassword("1q2w3e4r!0");
        ResponseEntity<?> login = memberController.login(loginDTO);
        assertThat(login).isEqualTo(true);

    }

    @Test
    public void 테스트_회원가입() throws MessagingException {
        int i = 25;
        MemberDTO memberDTO1 = MemberDTO.builder()
                .userName("장태환" + i)
                .password("1q2w3e4r!" + i)
                .birth("1992/" + i)
                .nickName("테스트" + i)
                .email("mail" + i + "@mail.com")
                .userName("user" + i)
                .registDate(LocalDateTime.now())
                .secessionDate(LocalDateTime.now().plusDays(5))
                .age(20 + i)
                .userId("testId" + i)
                .gender("남자")
                .mobile("010-1234-123" + i)
                .build();
        memberController.signUp(memberDTO1);






       // memberController.signUp()
    }

}