package team.crowdee.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.Withdrawal;
import team.crowdee.domain.dto.ChangePassDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.repository.MemberRepository;
import team.crowdee.repository.MemberRepositoryDohyun;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    /* @Test
     @Rollback(value = false)*/
    public void 더미데이터_삽입() {
        for (int i = 0; i < 10; i++) {
            memberService.join(
                    Member.builder()
                            .userName("장태환" + i)
                            .password("1q2w3e4r!" + i)
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

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = memberRepository.findById(1L);
        member.setWithdrawal(Withdrawal.existence);
        //when
        Member join = memberService.join(member);
        //then
        Assertions.assertThat(join).isNotNull();
    }

    // 테스트 완료
    @Test
    public void 아이디검증() {
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
        Assertions.assertThat(valiId1).isEqualTo(false);
        Assertions.assertThat(valiId2).isEqualTo(false);
        Assertions.assertThat(valiId3).isEqualTo(true);
    }

    @Test
    public void 비밀번호검증() {
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

        boolean valiPw1 = memberService.validationPw(samplePw1);
        boolean valiPw2 = memberService.validationPw(samplePw2);
        boolean valiPw3 = memberService.validationPw(samplePw3);
        boolean valiPw4 = memberService.validationPw(samplePw4);
        boolean valiPw5 = memberService.validationPw(samplePw5);

        //then
        Assertions.assertThat(valiPw1).isEqualTo(false);
        Assertions.assertThat(valiPw2).isEqualTo(false);
        Assertions.assertThat(valiPw3).isEqualTo(false);
        Assertions.assertThat(valiPw4).isEqualTo(false);
        Assertions.assertThat(valiPw5).isEqualTo(true);

    }

    // 테스트 완료
    @Test
    public void 아이디닉네임중복검증() {
        //given
        Member member = memberRepository.findById(1L); //유저네임 user0  닉네임 테스트0
        MemberDTO memberDTO1 = new MemberDTO();
        memberDTO1.setUserId(member.getUserId());//user0
        memberDTO1.setNickName("성두현");//닉네임 성두현
        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setUserId(member.getUserId());//user0
        memberDTO2.setNickName(member.getNickName());//닉네임 테스트0
        MemberDTO memberDTO3 = new MemberDTO();
        memberDTO3.setUserId("인생뭐있노");//유저네임 user0
        memberDTO3.setNickName("기모찌맨");//닉네임 기모찌맨
        MemberDTO memberDTO4 = new MemberDTO();
        memberDTO4.setUserId(member.getUserId());//유저네임 user0
        memberDTO4.setNickName("기모찌맨");//닉네임 기모찌맨
        //when
        boolean usernamecheck = memberService.doubleCheck(memberDTO2.getUserId(),memberDTO2.getNickName() );
        boolean nicknamecheck = memberService.doubleCheck(memberDTO4.getUserId(), memberDTO4.getNickName());
        Assertions.assertThat(usernamecheck).isEqualTo(false); // 유저네임중복이되냐 테스트
        Assertions.assertThat(nicknamecheck).isEqualTo(false); // 닉네임이중복이되냐 테스트


        //then


    }

    @Test
    public void 로그인테스트() {
        //give
        Member member = memberRepository.findById(1L);
        LoginDTO loginMember = new LoginDTO();
        loginMember.setUserId(member.getUserId());
        loginMember.setPassword("1q2w3e4r!0");
        loginMember.setWithdrawal(Withdrawal.existence);
        System.out.println(loginMember.getWithdrawal());
        System.out.println(loginMember.getUserId());
        //when
        Member memberLogin = memberService.memberLogin(loginMember);
        Assertions.assertThat(memberLogin).isNotNull();
        //then

    }

    //테스트 진행중
    @Test
    public void 회원정보수정() {
        //given
        //memberDTO 설정
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setZonecode("존코드");
        memberDTO.setRestAddress("레스트어드레스");
        memberDTO.setRoadAddress("로드어드레스");
        memberDTO.setMemberId(1L);
        memberDTO.setNickName("닉네임테스트");
        memberDTO.setPhone("000-0000-0000");
        memberDTO.setMobile("000-0000-0000");
        memberDTO.setPassword("pass");

        //when
        Member findMember = memberService.memberEdit(memberDTO);
        //then
        Assertions.assertThat(findMember.getNickName()).isEqualTo("닉네임테스트");
        Assertions.assertThat(findMember.getMobile()).isEqualTo("000-0000-0000");
        Assertions.assertThat(findMember.getPhone()).isEqualTo("000-0000-0000");
        Assertions.assertThat(findMember.getAddress().getZonecode()).isEqualTo("존코드");
        Assertions.assertThat(findMember.getAddress().getRestAddress()).isEqualTo("레스트어드레스");
        Assertions.assertThat(findMember.getAddress().getRoadAddress()).isEqualTo("로드어드레스");
        boolean matches = passwordEncoder.matches(memberDTO.getPassword(), findMember.getPassword());
        if (matches) {
            System.out.println("패스워드 일치");
        } else {
            System.out.println("패스워드 불일치");
        }
    }

    //테스트 진행중
    @Test
    public void 비밀번호수정() {
        //given //기존꺼로그인
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUserId("testId2");
        loginDTO.setPassword("1q2w3e4r!2");
        Member member = memberService.memberLogin(loginDTO);
        //Assertions.assertThat(member).isNotNull(); 로그인성공
        //비번체인지
        ChangePassDTO changePassDTO = new ChangePassDTO();
        changePassDTO.setMemberId(3L);
        changePassDTO.setOldPassword("1q2w3e4r!2");
        changePassDTO.setNewPassword("tjdengus1!");

        Member changmember = memberService.memberChangPass(changePassDTO);
        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), changmember.getPassword());
        //비번바꾸고 로그인
        loginDTO.setUserId("testId2");
        loginDTO.setPassword("tjdengus1!");
        Member member1 = memberService.memberLogin(loginDTO);
        Assertions.assertThat(member1).isNull();

    }

    @Test
    public void 회원탈퇴(){
        Member savemember = memberRepository.findById(1l);
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(1L);
        memberDTO.setUserId(savemember.getUserId());
        memberDTO.setPassword(passwordEncoder.encode(savemember.getPassword()));
        Member member = memberService.deleteMember(memberDTO);

        Assertions.assertThat(member.getMemberId()).isNotNull();

    }
}