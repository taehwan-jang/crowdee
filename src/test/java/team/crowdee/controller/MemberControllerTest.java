package team.crowdee.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.FindMailDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;

import javax.mail.MessagingException;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberControllerTest {
    @Autowired
    MemberController memberController;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    @Rollback(value = false)
    public void 더미데이터() {
        for (int i = 0; i < 10; i++) {
            memberRepository.save(
                    Member.builder()
                            .nickName("테스트" + i)
                            .email("mail" + i + "@mail.com")
                            .userName("user" + i)
                            .registDate(LocalDateTime.now())
                            .mobile("010-1234-123" + i)
                            .build()
            );
        }
    }

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

    @Test  //session 데이터없으면 널포인트뜨니까 넣고 테스트돌리세용!!
    public void 테스트_로그인(){
       LoginDTO loginDTO = new LoginDTO();
       loginDTO.setEmail("mail@mail.com");
       loginDTO.setPassword("1q2w3e4r!0");
       ResponseEntity<?> login = memberController.login(loginDTO);
       assertThat(login).isEqualTo(true);

    }

    @Test
    public void 회원가입_찾기(){
        FindMailDTO findMailDTO = new FindMailDTO();
        findMailDTO.setUserId("user59");
        findMailDTO.setEmail("Crowdee.funding@gmail.com");
        List<Member> user59 = memberRepository.findByEmail("Crowdee.funding@gmail.com");
        for (Member member : user59) {
            System.out.println(member+"어디지?");
        }
        System.out.println(user59+"여기인가?");

        ResponseEntity<?> responseEntity = memberController.lostPassword(findMailDTO);
        System.out.println(findMailDTO.getEmail());
        System.out.println(findMailDTO.getUserId());
        System.out.println(responseEntity);

    }

}
