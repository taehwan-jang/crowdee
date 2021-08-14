package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Authorities;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.ChangePassDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;
import team.crowdee.util.Utils;

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

    // 회원가입시 멤버 아이디 중복확인 어디?
    @Transactional
    public Member join(Member member) {
        boolean checkId = validationId(member);
        boolean checkPw = this.validationPw(member);
        boolean checkIdNick = this.doubleCheck(member.getUserId(), member.getNickName());
        if (checkId == false && checkPw == false && checkIdNick == false) {
            return null;
        }
        String encodePass = passwordEncoder.encode(member.getPassword());//패스워드 암호화
        member.changePassword(encodePass);//로직명 변경(명시적으로)
        Member saveMember = memberRepository.save(member);
        return saveMember != null ? member : null;

    }

    public Member memberLogin(LoginDTO loginDTO) {
        List<Member> userId = memberRepository.findByParam("userId", loginDTO.getUserId());
        if (userId.get(0).getSecessionDate() == null) {
            Member findMember = memberRepository.login(loginDTO.getUserId());
            boolean matches = passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword());
            return matches ? findMember : null;
        }
        else {
            return null;
        }
     //   결과값에 따라 return값 결정

//        if(loginDTO.getWithdrawal()==Withdrawal.existence){
//            Member findMember = memberRepository.login(loginDTO.getUserId());
//            //DB에 암호화된 패스워드와 입력한 패스워드가 일치하는지 확인하는 과정
//            System.out.println(matches);
//        }
//        else {
//            return null;
//        }

    }

    // 회원 ID 검증
    public boolean validationId(Member member){
        if(member.getUserId().length()<4 || member.getUserId().length()>20){
            return false;
        }
        return true;
    }

    // 회원 Password 검증
    public boolean validationPw(Member member){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        Matcher m = p.matcher(member.getPassword());
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
        Member member = memberRepository.findById(memberDTO.getMemberId());
        String encodePass = passwordEncoder.encode(memberDTO.getPassword());
        Address address = new Address();
        address.updateAddress(address);
     /*   address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());*/
       // member.changeMember(memberDTO.getNickName(),)
        member.changeNickName(memberDTO.getNickName());
        member.changePhone(memberDTO.getPhone());
        member.changeAddress(address);
        member.changeMobile(memberDTO.getMobile());
        member.changePassword(encodePass);
        return member != null ? member : null;
    }
    
    //비밀번호 수정
    @Transactional
    public Member memberChangPass(ChangePassDTO changePassDTO) {
        Member member = memberRepository.findById(changePassDTO.getMemberId()); //멤버에는 3번의 값이들어가잇다
        boolean matches = passwordEncoder.matches(changePassDTO.getOldPassword(), member.getPassword());
        System.out.println(matches);
        if(matches) {
            String encodePass = passwordEncoder.encode(changePassDTO.getNewPassword());//암호화한다음에
            System.out.println(encodePass);
            member.changePassword(encodePass);//저장
        }
        else {
            return null;
        }
        return member;

    }

    @Transactional
    public Member signUpConfirm(String email, String authKey) {
        List<Member> members = memberRepository.findToConfirm(email,authKey);
        if (members.isEmpty()) {
            return null;
        }
        Member member = members.get(0);
        member.setEmailCert("Y");
        member.setAuthorities(Authorities.backer);
        return member;
    }
  // <서비스 부분>
    @Transactional
    public Member deleteMember(MemberDTO memberDTO) { //비번 값을 보내준다고 가정
        Member member = memberRepository.findById(memberDTO.getMemberId());
        LocalDateTime currentDate= LocalDateTime.now();
        String plusMonths = currentDate.plusMonths(1L).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        log.info("탈퇴 신청 후 한달 이후 날짜 ={}", plusMonths);
        member.setSecessionDate(plusMonths);
        return member != null ? member : null;
    }

    @Scheduled(cron = "0 0 1 * * *") //0 0 1 * * *로 변경하면 하루마다 메소드 시작.
    @Transactional
    public void timeDelete() {
        String today= Utils.getTodayString();
        List<Member> SecessionMember = memberRepository.findByParam("secessionDate",today);
        if (SecessionMember != null && today!=null) {
            for (Member member : SecessionMember) {
                log.info("탈퇴 회원 아이디={}",member.getUserId());
                log.info("탈퇴 회원 이름={}",member.getUserName());
                log.info("탈퇴 회원 패스워드={}",member.getPassword());
                memberRepository.delete(member);
            }
        }



        /*
        for (int i=0; i<allMember.size(); i++) {
            //현재날짜가 탈퇴에서한달더한날짜를 초과한순간 true이니까 삭제시키면된다.
            if (allMember.get(i).getWithdrawal() == Withdrawal.unexistaence) {
                if (allMember.get(i).getRegistDate().isAfter(allMember.get(i).getSecessionDate())) {
                    memberRepository.delete(allMember.get(i).getMemberId());
                }
            }
            //미주코드
            if(allMember.get(i).getRegistDate()==Withdrawal.unexistaence) {
                if (currentDate.plusMonths(1).isAfter(allMember.get(i).getRegistDate().plusMonths(1))) {
                    Long longId = new Long((i+1));
                    memberRepository.delete(longId);
                }
            }
        }
         */

    }


}




