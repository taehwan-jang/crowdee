package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.LoginDTO;
import team.crowdee.repository.MemberRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private static MemberRepository memberRepository;
    @Transactional
    public Member join(Member member) {
        this.validation(member);
        this.doubleCheck(member.getUserId(),member.getNickName());
        memberRepository.save(member);
        return member;

    }
    @Transactional(readOnly = true)
    public Member memberLogin(LoginDTO loginDTO) {
        return  memberRepository.login(loginDTO.getUserId(), loginDTO.getPassword());

    }
    public boolean validation(Member member){
       Pattern p = Pattern.compile("\"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$\"\n");
       Matcher m = p.matcher(member.getPassword());
       if(member.getUserId().length()<4 || member.getUserId().length()>20) {
           return false;
       }
       if(m.matches()){
           return true;
       }
       return false;
    }

    @Transactional(readOnly = true)
    public boolean doubleCheck(String userId, String nickName) {
        Member byUserId = memberRepository.findByParam("userId",userId);
        System.out.println("byUserId"+byUserId);
        Member byNickName = memberRepository.findByParam("nickName",nickName);
        if (byUserId != null || byNickName !=null) {
            return false;
        }
        return true;
    }



}




