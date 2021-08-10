package team.crowdee.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Grade;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;
import team.crowdee.service.MemberService;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

//    @Test
//    @Rollback(value = false)
    public void 더미데이터_삽입() {
        for (int i = 0; i < 10; i++) {
            memberRepository.save(
                    Member.builder()
                            .userName("sdh30113"+i)
                            .password("tjdengus1!"+i)
                            .birth("1992/"+i)
                            .nickName("테스트"+i)
                            .email("mail"+i+ "@mail.com")
                            .userName("user"+i)
                            .registDate(LocalDateTime.now())
                            .age(20+i)
                            .userId("testId"+i)
                            .gender("남자")
                            .mobile("010-1234-123"+i)
                            .rank(Grade.BRONZE)
                            .build()
            );
        }
    }

    @Test
    public void 회원가입테스트 () throws Exception {
        //given
        Member member = new Member();
        member.setUserName("성두현");
        //when
        Member saveName = memberService.join(member);
        //then
        assertThat(saveName.equals("성두현"));

    }
    @Test //8-16자리 대문자소문자특수문자 포함
    public void 비밀번호검증() throws  Exception {
        //given
        Member member0 = new Member();
        member0.setPassword("1");
        Member member1 = new Member();
        member1.setPassword("11111111111111111111111111");

        //when

        //then

    }
    @Test
    public void 유저아이디유저닉네임중복확인() throws Exception {
        //given
//        Member member0 = memberRepository.findById(1L);
//        String userId = member0.getUserId();
//        String nickName = member0.getNickName();

        Member idCheck = new Member();
        idCheck.setUserId("testId0");
        idCheck.setNickName("테스트01");

        Member nickCheck = new Member();
        nickCheck.setUserId("testId0");
        nickCheck.setNickName("테스트01");
        //when
        boolean result = memberService.doubleCheck("testId0", "123");

        //then
        assertThat(result).isEqualTo(false);
    }


    @Test
    public void 로그인테스트() {
        Member member0 = memberRepository.findById(0L);


    }







}