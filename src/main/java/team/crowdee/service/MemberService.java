package team.crowdee.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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




