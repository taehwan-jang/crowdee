package team.crowdee.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.UserState;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.ChangePassDTO;
import team.crowdee.domain.dto.FindMailDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.MimeEmailService;
import team.crowdee.util.Utils;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MimeEmailService mimeEmailService;
    // 회원가입시 멤버 아이디 중복확인 어디?
    @Transactional
    public Long join(MemberDTO memberDTO) throws MessagingException {
        if ( validationPw(memberDTO) == false && validationNick(memberDTO) == false) {
            return null;
        }
        String authKey = mimeEmailService.sendAuthMail(memberDTO.getEmail());
        Member member = Member.builder()
                .password(passwordEncoder.encode(memberDTO.getPassword())) //패스워드암호화
                .userName(memberDTO.getUserName())
                .nickName(memberDTO.getNickName())
                .registDate(LocalDateTime.now())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .userState(UserState.guest)
                .emailCert(authKey)
                .build();

        Long saveMember = memberRepository.save(member);

        return saveMember;
    }

    //로그인 -> 토큰 추가로 인해 코드 리뷰 이후 코드작성
    public Member memberLogin(LoginDTO loginDTO) {
        System.out.println("로그인:"+  loginDTO.getPassword());
        System.out.println("로그인:"+loginDTO.getEmail());
        List<Member> email = memberRepository.findByEmail(loginDTO.getEmail());
        String email1 = email.get(0).getEmail();
        Member findMember = memberRepository.login(email1);
        if(findMember.getSecessionDate()==null ) {
            boolean matches = passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword());
            System.out.println(matches);

            return matches ? findMember : null;//결과값에 따라 return값 결정
        }
        return null;
    }

    //비밀번호 찾기
    public List<Member> findPassword(FindMailDTO findMailDTO) {
        List<Member> findPassMember = memberRepository
                .findByEmail(findMailDTO.getEmail());
        return findPassMember;
    }

    // 회원 Password 검증
    public boolean validationPw(MemberDTO memberDTO){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        Matcher m = p.matcher(memberDTO.getPassword());
        if(m.matches()){
            return true;
        }
        return false;
    }

    public boolean validationNick(MemberDTO memberDTO) {
        List<Member> byNickName = memberRepository.findByParam("nickName", memberDTO.getNickName());
        if (!byNickName.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean validationEmail(MemberDTO memberDTO) {
        List<Member> byEmail = memberRepository.findByParam("email", memberDTO.getEmail());
        if (!byEmail.isEmpty()) {
            return false;
        }
        return true;

    }

    //회원 정보 수정
    @Transactional
    public Member memberEdit(MemberDTO memberDTO) {
        //닉네임 중복 검사
        if(validationNick(memberDTO)==false) {
            return null;
        }
        Member member = memberRepository.findById(memberDTO.getMemberId());
        member.changeNickName(memberDTO.getNickName())
                .changeMobile(memberDTO.getMobile());
        return member;
    }

    //비밀번호 수정
    @Transactional
    public Member memberChangPass(ChangePassDTO changePassDTO) {
        Member member = memberRepository.findById(changePassDTO.getMemberId());
        boolean matches = passwordEncoder.matches(changePassDTO.getOldPassword(), member.getPassword());
        System.out.println(matches);
        if(matches) {
            boolean pw = this.validationChangPass(changePassDTO);
            if(pw==false){
                return null;
            }
            String encodePass = passwordEncoder.encode(changePassDTO.getNewPassword());//암호화한다음에
            member.changePassword(encodePass);//저장
            System.out.println(encodePass);
            return member;

        }
        return null;
    }

    //ChangePassDTO의 NewPassword 유효성검사
    public boolean validationChangPass(ChangePassDTO changePassDTO){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        Matcher m = p.matcher(changePassDTO.getNewPassword());
        if(m.matches()){
            return true;
        }
        return false;
    }

    //이메일 인증
    @Transactional
    public Member signUpConfirm(String email, String authKey) {
        List<Member> members = memberRepository.findToConfirm(email,authKey);
        if (members.isEmpty()) {
            return null;
        }
        Member member = members.get(0);
        member.setEmailCert("Y");
        member.setUserState(UserState.backer);
        return member;
    }

    //회원탈퇴
    @Transactional
    public Member deleteMember(MemberDTO memberDTO) { //비번 값을 보내준다고 가정
        Member findMember = memberRepository.findByParam("userName", memberDTO.getUserName()).get(0);
        LocalDateTime currentDate= LocalDateTime.now();
        String plusMonths = currentDate.plusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("탈퇴 신청 후 한달 이후 날짜 ={}", plusMonths);
        findMember.secessionMember(plusMonths);
        return findMember;
    }

    //회원탈퇴 : 매일 0시마다 실행되는 로직, 일자 비교하여 회원삭제
    @Scheduled(cron = "0 0 1 * * *") //0 0 1 * * *로 변경하면 하루마다 메소드 시작.
    @Transactional
    public void timeDelete() {
        String today= Utils.getTodayString();
//        String today = "20210815"; 테스트바꿀때 일자 바꿔서해볼것
        List<Member> SecessionMember = memberRepository.findByParam("secessionDate",today);
        for (Member member : SecessionMember) {
            memberRepository.delete(member);
        }
    }

}




