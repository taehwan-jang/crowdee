package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    private Member join(Member member) {
        this.validation(member);
        memberRepository.save(member);
        return member;

    }

   private  boolean validation(Member member){
       Pattern p = Pattern.compile("\"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$\"\n");
       Matcher m = p.matcher(member.getPassword());
       if(member.getUserId().length()<4 || member.getUserId().length()>20) {
           return false;
       }
       if(m.matches()){
           return true;
       }
       return false;
   }

}




