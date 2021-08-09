package team.crowdee.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long join(Member member) {

        memberRepository.save(member);
        return member.getMemberId();
    }



}
