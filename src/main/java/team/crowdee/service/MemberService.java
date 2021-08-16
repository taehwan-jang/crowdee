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
import team.crowdee.domain.valuetype.Address;
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
        Address address = new Address();
        address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());
        String authKey = mimeEmailService.sendAuthMail(memberDTO.getEmail());
        Member member = Member.builder()
                .userId(memberDTO.getUserId())
                .password(passwordEncoder.encode(memberDTO.getPassword())) //패스워드암호화
                .userName(memberDTO.getUserName())
                .nickName(memberDTO.getNickName())
                .gender(memberDTO.getGender())
                .age(memberDTO.getAge())
                .birth(memberDTO.getBirth())
                .address(address)
                .phone(memberDTO.getPhone())
                .registDate(LocalDateTime.now())
                .mobile(memberDTO.getMobile())
                .email(memberDTO.getEmail())
                .userState(UserState.guest)
                .emailCert(authKey)
                //autories 랑
                .build();
        if (validationId(memberDTO) == false && validationPw(memberDTO) == false &&
                doubleCheck(memberDTO.getUserId(), memberDTO.getNickName()) == false) {
            return null;
        }
        Long saveMember = memberRepository.save(member);
        return saveMember;

    }

    //로그인 -> 토큰 추가로 인해 코드 리뷰 이후 코드작성
    public Member memberLogin(LoginDTO loginDTO) {
        Member findMember = memberRepository.login(loginDTO.getUserId());
        if(findMember.getSecessionDate()==null) {
            boolean matches = passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword());
            return matches ? findMember : null;//결과값에 따라 return값 결정
        }
        return null;
    }

    //비밀번호 찾기
    public List<Member> findPassword(FindMailDTO findMailDTO) {

        List<Member> findPassMember = memberRepository
                .findByEmailAndUserId(findMailDTO.getUserId(), findMailDTO.getEmail());
        return findPassMember;
    }

    // 회원 ID 검증
    public boolean validationId(MemberDTO memberDTO){
        if(memberDTO.getUserId().length()<4 || memberDTO.getUserId().length()>20){
            return false;
        }
        return true;
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

    public boolean doubleCheck(String userId, String nickName) {
        List<Member> byUserId = memberRepository.findByParam("userId", userId);
        List<Member> byNickName = memberRepository.findByParam("userId", nickName);
        //값이들어온상태인데 비엇다고하면 차잇다면 펄스인데
        if (!byUserId.isEmpty() || !byNickName.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean doubleCheck(String nickName) {
        List<Member> byNickName = memberRepository.findByParam("userId", nickName);
        if (!byNickName.isEmpty()) {
            return false;
        }
        return true;
    }

    //회원 정보 수정
    @Transactional
    public Member memberEdit(MemberDTO memberDTO) {
        //닉네임 중복 검사
        List<Member> byNickName = memberRepository.findByParam("nickName", memberDTO.getNickName());
        if (!(byNickName.isEmpty())) {
            return null;
        }
        Member member = memberRepository.findById(memberDTO.getMemberId());
        Address address = new Address();
        address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());
        member = Member.builder()
                .nickName(memberDTO.getNickName())
                .address(address)
                .phone(memberDTO.getPhone())
                .mobile(memberDTO.getMobile())
                .build();

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
        Member findMember = memberRepository.findByParam("userId", memberDTO.getUserId()).get(0);
        System.out.println("findMember.getUserId()1:"+findMember.getUserId());
        LocalDateTime currentDate= LocalDateTime.now();
        String plusMonths = currentDate.plusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("탈퇴 신청 후 한달 이후 날짜 ={}", plusMonths);
        findMember.changeSecessionDate(plusMonths);
        return findMember;
    }

    //회원탈퇴 : 매일 0시마다 실행되는 로직, 일자 비교하여 회원삭제
    @Scheduled(cron = "50 43 11 * * *") //0 0 1 * * *로 변경하면 하루마다 메소드 시작.
    @Transactional
    public void timeDelete() {
        String today= Utils.getTodayString();
//        String today = "20210815";
        List<Member> SecessionMember = memberRepository.findByParam("secessionDate",today);
        for (Member member : SecessionMember) {
            System.out.println("실행하니?");
            log.info("탈퇴 회원 아이디={}",member.getUserId());
            log.info("탈퇴 회원 이름={}",member.getUserName());
            log.info("탈퇴 회원 패스워드={}",member.getPassword());
            memberRepository.delete(member);
        }
    }

}




