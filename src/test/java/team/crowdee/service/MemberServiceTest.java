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

  /*      @Test
      @Rollback(value = false)
      public void 더미데이터_삽입() {
        for (int i = 0; i < 10; i++) {
            memberService.join(
                    Member.builder()
                            .userName("장태환" + i)
                            .password("1q2w3e4r!" + i)
                            .birth("1992/" + i)
                            .nickName("테스트" + i)
                            .email("crowdee.funding@gmail.com")
                            .userName("user" + i)
                            .registDate(LocalDateTime.now())
                            .age(20 + i)
                            .userId("testId" + i)
                            .gender("남자")
                            .mobile("010-1234-123" + i)
                            .build()
            );
        }
    }*/

    @Test //멤버컨트롤러 값 들어가는지 테스트완료
    @Rollback(value = false)
    public void 테스트_회원가입() throws MessagingException {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setZonecode("존코드11");
        memberDTO.setRoadAddress("도로명주소11");
        memberDTO.setRestAddress("레스트어드레스11");
        memberDTO.setUserId("sdh301113");
        memberDTO.setPassword("tjdengus1!");
        memberDTO.setNickName("미주짱짱맨");
        memberDTO.setEmail("crowdee.funding@gmail.com");
        memberService.join(memberDTO);
    }
        // 테스트 완료
    @Test
    public void 아이디검증() {
        //given
        MemberDTO sampleId1 = new MemberDTO();
        sampleId1.setUserId("abc"); //4글자 미만
        MemberDTO sampleId2 = new MemberDTO();
        sampleId2.setUserId("asdfghjklzxcvbnmqwertyy"); //20글자 초과
        MemberDTO sampleId3 = new MemberDTO();
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
        boolean usernamecheck = memberService.doubleCheck(memberDTO2.getUserId(),memberDTO2.getNickName());
        boolean nicknamecheck = memberService.doubleCheck(memberDTO4.getUserId(), memberDTO4.getNickName());
        //thn
        Assertions.assertThat(usernamecheck).isEqualTo(false); // 유저네임중복이되냐 테스트
        Assertions.assertThat(nicknamecheck).isEqualTo(false); // 닉네임이중복이되냐 테스트

    }

  /*  @Test //토근추가후 테스트 재실행필요
    public void 로그인테스트() {
        //give
        Member member = memberRepository.findById(1L);
        LoginDTO loginMember = new LoginDTO();
        loginMember.setUserId(member.getUserId());
        loginMember.setPassword("1q2w3e4r!0");

        //when
        Member getLoginMember = memberService.memberLogin(loginMember);

        //then
         Assertions.assertThat(getLoginMember).isNotNull();
        Assertions.assertThat(getLoginMember.getUserId()).isEqualTo(member.getUserId());
        boolean matches = passwordEncoder.matches(loginMember.getPassword(), getLoginMember.getPassword());
        if(matches) {
            System.out.println("비밀번호 일치");
        }
        else {
            System.out.println("비밀번호 불일치");
        }
    }*/
  //토근추가후 테스트 재실행필요
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
        //when
        Member findMember = memberService.memberEdit(memberDTO);
        //then
        Assertions.assertThat(findMember.getNickName()).isEqualTo("닉네임테스트");
        Assertions.assertThat(findMember.getMobile()).isEqualTo("000-0000-0000");
        Assertions.assertThat(findMember.getPhone()).isEqualTo("000-0000-0000");
        Assertions.assertThat(findMember.getAddress().getZonecode()).isEqualTo("존코드");
        Assertions.assertThat(findMember.getAddress().getRestAddress()).isEqualTo("레스트어드레스");
        Assertions.assertThat(findMember.getAddress().getRoadAddress()).isEqualTo("로드어드레스");

    }

    //토근추가후 테스트 재실행필요
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

    //토근추가후 테스트 재실행필요
    @Test
//    @Rollback(value = false)
    public void 회원탈퇴_SecessionDate_확인(){
        //given
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserId("testId4");

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

    @Test
    public void 비밀번호찾기() {

        //given
        Member memberFindPass = memberRepository.findById(2L);
        FindMailDTO findMailDTO = new FindMailDTO();
        findMailDTO.setUserId(memberFindPass.getUserId());
        findMailDTO.setEmail(memberFindPass.getEmail());
        System.out.println(findMailDTO.getEmail());
        System.out.println(findMailDTO.getUserId());

        //when
        List<Member> findMember = memberService.findPassword(findMailDTO);
        if (findMember == null) {
            System.out.println("멤버 들고오기 실패");
        }
        Member member = findMember.get(0);
        MailDTO mailDTO = sendEmailService.createMailAndChangePass(member.getEmail(), member.getUserName(), member.getMemberId());
        if(mailDTO==null) {
            System.out.println("비밀번호 변경실패");
        }

        //then
        Assertions.assertThat(member.getUserName()).isEqualTo(memberFindPass.getUserName());
        sendEmailService.sendMail(mailDTO);

    }

}
