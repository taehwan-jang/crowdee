package team.crowdee.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.repository.MemberRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Member join(Member member) {
        this.validationId(member);
        this.validationPw(member);
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
        System.out.println(member.getUserId());

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

    public Member findId(Member member) {
       return memberRepository.findById(member.getMemberId());
    }

/*
    public Member findPass(Member member) {
        //return memberRepository.findByParam("")
    }
*/




}




