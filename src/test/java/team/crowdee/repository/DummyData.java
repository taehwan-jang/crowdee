package team.crowdee.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Authority;
import team.crowdee.domain.Member;
import team.crowdee.domain.UserState;
import team.crowdee.domain.dto.CreatorDTO;
import team.crowdee.domain.dto.MemberDTO;
import team.crowdee.service.CreatorService;
import team.crowdee.service.MemberService;

import java.time.LocalDateTime;
import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class DummyData {

    @Autowired
    private MemberService memberService;
    @Autowired
    private CreatorService creatorService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CreatorRepository creatorRepository;
    @Autowired
    private FundingRepository fundingRepository;

    @Test
    @Rollback(value = false)
    public void 멤버_더미데이터() throws Exception {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setEmail("forMember@gmail.com");
        memberDTO.setPassword("1q2w3e4r!");
        memberDTO.setEmailCert("TAWOETHD");
        memberDTO.setMobile("010-1231-1231");
        memberDTO.setNickName("테스트닉네임");
        memberDTO.setRegistDate(LocalDateTime.now());
        memberDTO.setUserName("크라우디");

        memberService.join(memberDTO);

        MemberDTO memberDTO2 = new MemberDTO();
        memberDTO2.setEmail("forCreator@gmail.com");
        memberDTO2.setPassword("1q2w3e4r!");
        memberDTO2.setEmailCert("WEFADFWE");
        memberDTO2.setMobile("010-1231-1231");
        memberDTO2.setNickName("크리에이터");
        memberDTO2.setRegistDate(LocalDateTime.now());
        memberDTO2.setUserName("창작자");

        memberService.join(memberDTO2);
    }

    @Test
    @Rollback(value = false)
    public void 크리에이터_더미데이터() {
        CreatorDTO creatorDTO = new CreatorDTO();
        creatorDTO.setAccountNumber("123-123123-123");
        creatorDTO.setBankName("신한");
        creatorDTO.setBusinessNumber("1239192-12-12");
        creatorDTO.setCreatorNickName("투썸");
        creatorDTO.setBankBookImageUrl("imgu");
        creatorDTO.setMemberId(3L);

        creatorService.joinCreator(creatorDTO);
    }
}
