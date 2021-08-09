package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private MemberRepository memberRepository;

    private Member join(Member member) {
        //this.validate(member);
        memberRepository.save(member);
        return member;

    }

  /*  private Boolean validate(Member member) {

    }*/



}
