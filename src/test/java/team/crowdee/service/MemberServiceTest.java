package team.crowdee.service;

import org.assertj.core.api.Assertions;
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

    @Test
    public void 아이디검증(){
        //given
        Member sampleId1 = new Member();
        sampleId1.setUserId("abc"); //4글자 미만
        Member sampleId2 = new Member();
        sampleId2.setUserId("asdfghjklzxcvbnmqwertyy"); //20글자 초과
        Member sampleId3 = new Member();
        sampleId3.setUserId("yoohansol"); //조건에 충족하는 값

        //when
        boolean valiId1 = memberService.validationId(sampleId1);
        boolean valiId2 = memberService.validationId(sampleId2);
        boolean valiId3 = memberService.validationId(sampleId3);

        //then
        Assertions.assertThat(valiId1 == false);
        Assertions.assertThat(valiId2 == false);
        Assertions.assertThat(valiId3 == true);
    }

    @Test
    public void 비밀번호검증(){
        //given
        Member samplePw1 = new Member();
        samplePw1.setUserId("abc"); //4글자 미만
        Member samplePw2 = new Member();
        samplePw2.setUserId("asdfghjklzxcvbnmqwertyy"); //20글자 초과
        Member samplePw3 = new Member();
        samplePw3.setUserId("yoohansol"); //조건에 충족하는 값

        //when
        boolean valiPw1 = memberService.validationId(samplePw1);
        boolean valiPw2 = memberService.validationId(samplePw2);
        boolean valiPw3 = memberService.validationId(samplePw3);

        //then
        Assertions.assertThat(valiPw1 == false);
        Assertions.assertThat(valiPw2 == false);
        Assertions.assertThat(valiPw3 == true);
    }

    @Test
    public void 유저아이디유저닉네임중복확인() {
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
        boolean idResult = memberService.doubleCheck("testId0", "123");
        boolean nickResult = memberService.doubleCheck("testId0", "123");
        //then
        assertThat(idResult).isEqualTo(nickResult).isEqualTo(false);
    }


    @Test
    public void 로그인테스트() {
        Member member0 = memberRepository.findById(0L);


    }







}