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
import team.crowdee.controller.MemberController;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.*;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.SendEmailService;
import javax.mail.MessagingException;
import java.util.List;

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
    @Autowired
    private MemberController memberController;
    @Autowired
    private SendEmailService sendEmailService;


   /* @Test
   // @Rollback(value = false)
    public void 더미데이터_삽입() throws MessagingException {
        MemberDTO memberDTO = new MemberDTO();
        for (int i = 0; i < 10; i++) {
            memberDTO.setUserName("장태환" + i);
            memberDTO.setPassword("1q2w3e4r!" + i);
            memberDTO.setNickName("테스트" + i);
            memberDTO.setEmail("crowdee.funding@gmail.com");
            memberDTO.setUserName("user" + i);
            memberDTO.setMobile("010-1234-123" + i);
            memberService.join(memberDTO);
        }

    }*/

    @Test //멤버컨트롤러 값 들어가는지 테스트완료
    @Rollback(value = false)
    public void 테스트_회원가입() throws MessagingException {
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setPassword("tjdengus1!");
        memberDTO.setNickName("미주짱짱맨");
        memberDTO.setEmail("crowdee.funding@gmail.com");
        memberService.join(memberDTO);
    }
        // 테스트 완료

    @Test
    public void 비밀번호검증() {
        //given
        MemberDTO samplePw1 = new MemberDTO();
        samplePw1.setPassword("123456"); //8글자 미만
        MemberDTO samplePw2 = new MemberDTO();
        samplePw2.setPassword("12345678901234567"); //16글자 초과
        MemberDTO samplePw3 = new MemberDTO();
        samplePw3.setPassword("1234567890"); //숫자로만 구성
        MemberDTO samplePw4 = new MemberDTO();
        samplePw4.setPassword("aaaaa11111"); //영어로만 구성
        MemberDTO samplePw5 = new MemberDTO();
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

    @Test
    public void 닉네임중복검증() {
        //given
        MemberDTO nickname = new MemberDTO();
        nickname.setNickName("테스트0");
        memberService.validationNick(nickname);
        //when
        //when
        boolean validationNick = memberService.validationNick(nickname);
        System.out.println(validationNick);

        //thn// 유저네임중복이되냐 테스트
        Assertions.assertThat(validationNick).isEqualTo(false); // 닉네임이중복이되냐 테스트

    }

  @Test //토근추가후 테스트 재실행필요
    public void 로그인테스트() {
        //give
        LoginDTO loginMember = new LoginDTO();
        loginMember.setEmail("crowdee.funding@gmail.com");
        loginMember.setPassword("1q2w3e4r!0");
        //when
        Member getLoginMember = memberService.memberLogin(loginMember);
        //then
         Assertions.assertThat(getLoginMember).isNotNull();
        boolean matches = passwordEncoder.matches(loginMember.getPassword(), getLoginMember.getPassword());
        if(matches) {
            System.out.println("비밀번호 일치");
        }
        else {
            System.out.println("비밀번호 불일치");
        }
    }

  //토근추가후 테스트 재실행필요
    @Test
    public void 회원정보수정() {
       //given
        //memberDTO 설정
        MemberDTO memberDTO = new MemberDTO();

        memberDTO.setMemberId(1L);
        memberDTO.setNickName("닉네임테스트");
        memberDTO.setMobile("000-0000-0000");
        //when
        Member findMember = memberService.memberEdit(memberDTO);
        //then
        Assertions.assertThat(findMember.getNickName()).isEqualTo("닉네임테스트");
        Assertions.assertThat(findMember.getMobile()).isEqualTo("000-0000-0000");

    }

    @Test
    public void 비밀번호수정() {
        //given
        LoginDTO loginDTO = new LoginDTO();
        ChangePassDTO changePassDTO = new ChangePassDTO();
        changePassDTO.setMemberId(1L);
        changePassDTO.setOldPassword("1q2w3e4r!0");
        changePassDTO.setNewPassword("tjdengus1!");
        //비밀번호 수정
//       changePassDTO.setNewPassword("비번"); //유효성검사통과못하면 null반환
        Member changMember = memberService.memberChangPass(changePassDTO);
        boolean matches = passwordEncoder.matches(changePassDTO.getNewPassword(), changMember.getPassword());
        Assertions.assertThat(matches).isEqualTo(true);
        //비밀번호 수정 후 로그인하여 확인
        loginDTO.setEmail("crowdee.funding@gmail.com");
        loginDTO.setPassword("tjdengus1!");
        Member memberLogin = memberService.memberLogin(loginDTO);
        Assertions.assertThat(memberLogin).isNotNull();
    }
    //토근추가후 테스트 재실행필요
    @Test
   // @Rollback(value = false)
    public void 회원탈퇴_SecessionDate_확인(){
        //given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("user0");

        //when
        Member deleteMember = memberService.deleteMember(memberDTO);

        //then
        System.out.println("deleteMember.getUserName() = " + deleteMember.getUserName());
        String secessionDate = deleteMember.getSecessionDate();
        System.out.println("secessionDate = " + secessionDate);
        Assertions.assertThat(secessionDate).isEqualTo("20210916");
    }

    @Test
    public void 회원탈퇴_SecessionDate_매일확인() {
        //given
        Member member = memberRepository.findById(1L);
        member.setSecessionDate("20210816");

        //when
        memberService.timeDelete();

        //then
        if(memberRepository.findById(1L)==null) {
            System.out.println("회원삭제완료");
        }
    }


    public void 비밀번호찾기 (){
        //given
        Member memberFindPass = memberRepository.findById(2L);
        Member member = memberRepository.findById(2L);
        FindMailDTO findMailDTO = new FindMailDTO();
        findMailDTO.setEmail(memberFindPass.getEmail());
        System.out.println(findMailDTO.getEmail());
        System.out.println(findMailDTO.getUserId());
        //when
        List<Member> findMember = memberService.findPassword(findMailDTO);
        if (findMember == null) {
            System.out.println("멤버 들고오기 실패");
        }
            Member member1 = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName(), member.getMemberId());
        if(mailDTO==null) {
            System.out.println("비밀번호 변경실패");
        }
        //then
        Assertions.assertThat(member.getUserName()).isEqualTo(memberFindPass.getUserName());
        sendEmailService.sendMail(mailDTO);
        memberController.lostPassword(findMailDTO);

    }

}
