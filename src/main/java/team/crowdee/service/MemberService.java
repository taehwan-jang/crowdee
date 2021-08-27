package team.crowdee.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Authority;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Transactional
    public Long join(MemberDTO memberDTO) {
        //비밀번호검증:8-16자리 대문자소문자특수문자 포함 // 닉네임 중복검증 //이메일 중복검증 형식검증
        if (validationPw(memberDTO) == false && validationNick(memberDTO) == false && validationEmail(memberDTO) == false) {
            return null;
        }
        Set<Authority> authorities = new HashSet<Authority>();
        authorities.add(Authority.builder().authorityName("backer").build());
        authorities.add(Authority.builder().authorityName("creator").build());
        Member member = Member.builder()
                .password(passwordEncoder.encode(memberDTO.getPassword())) //패스워드암호화
                .userName(memberDTO.getUserName())
                .nickName(memberDTO.getNickName())
                .registDate(LocalDateTime.now())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .emailCert(memberDTO.getEmailCert())
                .authorities(authorities)
                .userState(UserState.backer)
                .build();
        Long saveMember = memberRepository.save(member);
        return member.getMemberId();
    }

    //로그인 -> 토큰 추가로 인해 코드 리뷰 이후 코드작성
    public Member memberLogin(LoginDTO loginDTO) {
        log.info("로그인비밀번호 ={}", loginDTO.getEmail());
        log.info("로그인비밀번호 ={}", loginDTO.getPassword());
        List<Member> findMember = memberRepository.findByEmail(loginDTO.getEmail());
        if (findMember.isEmpty()) {
            return null;
        }
        if(findMember.get(0).getSecessionDate()==null ) {
            boolean matches = passwordEncoder.matches(loginDTO.getPassword(), findMember.get(0).getPassword());
            return matches ? findMember.get(0) : null;//결과값에 따라 return값 결정
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
        if (memberDTO.getPassword() == null) {
            return false;
        }
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        Matcher m = p.matcher(memberDTO.getPassword());
        if(m.matches()){
            return true;
        }
        return false;
    }

    public boolean validationNick(MemberDTO memberDTO) {
        if (memberDTO.getNickName() == null) {
            return false;
        }
        List<Member> byNickName = memberRepository.findByParam("nickName", memberDTO.getNickName());
        if (!byNickName.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean validationEmail(MemberDTO memberDTO) {
        //값을입력안했을때
        if (memberDTO.getEmail() == null) {
            return false;
        }
        //이메일 형식체크
        if (checkEmail(memberDTO)==false) {
            return false;
        }
        List<Member> byEmail = memberRepository.findByParam("email", memberDTO.getEmail());
        //이메일 중복검사
        if (!byEmail.isEmpty()) {
            return false;
        }
        return true;

    }

    public boolean checkEmail(MemberDTO memberDTO) { // .과 @ 를 무조건포함시켜야한다.
        Pattern p = Pattern.compile("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$");
        Matcher m = p.matcher(memberDTO.getEmail());
        if(m.matches()){
            return true;
        }
        return false;

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
    public Member memberChangePass(ChangePassDTO changePassDTO) {
        //1.newPassword검증
        boolean newChangePass = validationChangePass(changePassDTO);
        if(newChangePass==false){
            return null;
        }
        //2.ChangePassDTO의 memberId와 일치하는 Member정보 가져와서 비밀번호 비교
        Member member = memberRepository.findById(changePassDTO.getMemberId());
        boolean matches = passwordEncoder.matches(changePassDTO.getOldPassword(), member.getPassword());
        //3.일치하면 암호화하여 Member의 changePassword를 통해 password변경
        if(matches) {
            String encodePass = passwordEncoder.encode(changePassDTO.getNewPassword());
            member.changePassword(encodePass);
            log.info("암호화비밀번호 ={}", encodePass);
            return member;

        }
        return null;
    }

    //ChangePassDTO의 NewPassword 유효성검사
    public boolean validationChangePass(ChangePassDTO changePassDTO){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        if(p.matcher(changePassDTO.getNewPassword()).matches()){
            return true;
        }
        return false;
    }
    //회원탈퇴
    @Transactional
    public Member deleteMember(MemberDTO memberDTO) { //비번 값을 보내준다고 가정
        Member findMember = memberRepository.findByParam("nickName", memberDTO.getNickName()).get(0);
        LocalDateTime currentDate = LocalDateTime.now();
        //한달뒤의 일자를 넣어줌.
        String plusMonths = currentDate.plusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("탈퇴 신청 후 한달 이후 날짜 ={}", plusMonths);
        //세션데이트 컬럼에 한달뒤의 일자를 보내줌.
        findMember.changeSecessionDate(plusMonths);
        return findMember;
    }

    //회원탈퇴 : 매일 0시마다 실행되는 로직, 일자 비교하여 회원삭제
    @Scheduled(cron = "0 0 1 * * *") //0 0 1 * * *로 변경하면 하루마다 메소드 시작.
    @Transactional
    public void timeDelete() {
        String today= Utils.getTodayString();
//        String today = "20210815"; 테스트바꿀때 일자 바꿔서해볼것
        // 현재날짜와 일치한 데이터를 가져와서 저장함.
        List<Member> SecessionMember = memberRepository.findByParam("secessionDate",today);
        for (Member member : SecessionMember) {
            // forEach을 돌려서 동일한 멤버 삭제
            memberRepository.delete(member);
        }
    }

    public String getUserNickName(String email) {
        List<Member> byEmail = memberRepository.findByEmail(email);
        if (byEmail.isEmpty()) {
            throw new IllegalArgumentException("회원이 아닙니다.");
        }
        return byEmail.get(0).getNickName();
    }

}




