package team.crowdee.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Authorities;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.ChangePassDTO;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.domain.valuetype.Address;
import team.crowdee.repository.MemberRepository;

import javax.persistence.EntityManagerFactory;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    // 로직 수정 필요
    @Transactional
    public Member join(Member member) {
        this.validationId(member);
//        this.validationPw(member);
        this.doubleCheck(member.getUserId(),member.getNickName());
        String encodePass = passwordEncoder.encode(member.getPassword());//패스워드 암호화
        member.setPassword(encodePass);//암호화된 패스워드 저장
        memberRepository.save(member);
        return member;

    }

    @Transactional(readOnly = true)
    public Member memberLogin(LoginDTO loginDTO) {
        Member findMember = memberRepository.login(loginDTO.getUserId());
        //DB에 암호화된 패스워드와 입력한 패스워드가 일치하는지 확인하는 과정
        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), findMember.getPassword());
        return matches ? findMember : null;//결과값에 따라 return값 결정
    }

    // 회원 ID 검증
    @Transactional
    public boolean validationId(Member member){
        if(member.getUserId().length()<4 || member.getUserId().length()>20){
            return false;
        }
        return true;
    }

    // 회원 Password 검증
    @Transactional
    public boolean validationPw(Member member){
        Pattern p = Pattern.compile("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,16}$");
        Matcher m = p.matcher(member.getPassword());
        if(m.matches()){
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public boolean doubleCheck(String userId, String nickName) {
        List<Member> byUserId = memberRepository.findByParam("userId", userId);
        List<Member> byNickName = memberRepository.findByParam("userId", nickName);
        if (!byUserId.isEmpty() || !byNickName.isEmpty()) {
            return false;
        }
        return true;
    }

    @Transactional(readOnly = true)
    public boolean doubleCheck(String nickName) {
        List<Member> byNickName = memberRepository.findByParam("userId", nickName);
        if (!byNickName.isEmpty()) {
            return false;
        }
        return true;
    }


    public Member findId(Member member) {
       return memberRepository.findById(member.getMemberId());
    }


    //회원 정보 수정
    @Transactional
    public Member memberEdit(MemberDTO memberDTO) {

        Member member = memberRepository.findById(memberDTO.getMemberId());
        String encodePass = passwordEncoder.encode(memberDTO.getPassword());

        Address address = new Address();
        address.setZonecode(memberDTO.getZonecode());
        address.setRestAddress(memberDTO.getRestAddress());
        address.setRoadAddress(memberDTO.getRoadAddress());

        member.changeNickName(member.getNickName());
        member.changePhone(member.getPhone());
        member.changeAddress(member.getAddress());
        member.changeMobile(member.getMobile());
        member.changePassword(encodePass);

        return member;

    }
    
    //비밀번호 수정
    @Transactional
    public Member memberChangPass(ChangePassDTO changePassDTO) {

        Member member = memberRepository.findById(changePassDTO.getMemberId());

        boolean matches = passwordEncoder.matches(changePassDTO.getOldPassword(), member.getPassword());
        if(matches) {
            String encodePass = passwordEncoder.encode(changePassDTO.getNewPassword());
            member.changePassword(encodePass);
        }
        else {
            return null;
        }
        return member;
    }

    @Transactional
    public Member signUpConfirm(String email, String authKey) {
        List<Member> members = memberRepository.findToConfirm(email,authKey);
        if (!members.isEmpty()) {
            return null;
        }
        Member member = members.get(0);
        member.setEmailCert("Y");
        member.setAuthorities(Authorities.backer);
        memberRepository.flush();
        return member;
    }
}




