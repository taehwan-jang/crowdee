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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void 회원가입 () throws Exception {
        //given
        Member member = new Member();
        member.setUserName("코스모");
        //when
        Member saveName = memberService.join(member);
        //then
        assertThat(saveName.equals("코스모"));
    }

    // 테스트 완료
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
        System.out.println("valiId1 = " + valiId1);
        System.out.println("valiId2 = " + valiId2);
        System.out.println("valiId3 = " + valiId3);
    }

    @Test
    public void 비밀번호검증(){
        //given
        Member samplePw1 = new Member();
        samplePw1.setPassword("123456"); //8글자 미만
        Member samplePw2 = new Member();
        samplePw2.setPassword("12345678901234567"); //16글자 초과
        Member samplePw3 = new Member();
        samplePw3.setPassword("1234567890"); //숫자로만 구성
        Member samplePw4 = new Member();
        samplePw4.setPassword("aaaaa11111"); //영어로만 구성
        Member samplePw5 = new Member();
        samplePw5.setPassword("Gksthf1234@"); //조건에 충족하는 값


        Pattern p = Pattern.compile("\"^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,16}$\"\n");
        Matcher m = p.matcher(samplePw4.getPassword());

//        boolean valiPw1 = memberService.validationPw(samplePw1);
//        boolean valiPw2 = memberService.validationPw(samplePw2);
//        boolean valiPw3 = memberService.validationPw(samplePw3);
//        boolean valiPw4 = memberService.validationPw(samplePw4);
//        boolean valiPw5 = memberService.validationPw(samplePw5);

        //then
//        System.out.println("valiPw1 = " + valiPw1);
//        System.out.println("valiPw2 = " + valiPw2);
//        System.out.println("valiPw3 = " + valiPw3);
//        System.out.println("valiPw4 = " + valiPw4);
        System.out.println("Matcher = " + m.matches());

    }

    // 테스트 완료
    @Test
    public void 아이디닉네임중복검증() {
        //given
        Member idNickCheck1 = new Member();
        idNickCheck1.setUserId("testId0");
        idNickCheck1.setNickName("테스트01");

        Member idNickCheck2 = new Member();
        idNickCheck2.setUserId("testId0");
        idNickCheck2.setNickName("테스트01");

        //when
        boolean idResult = memberService.doubleCheck("testId0", "123");
        boolean nickResult = memberService.doubleCheck("testId0", "123");

        //then
        assertThat(idResult).isEqualTo(nickResult).isEqualTo(true);
    }

    // 테스트 완료
    @Test
    public void 로그인테스트() {
        Member member0 = memberRepository.findById(0L);

    }

}