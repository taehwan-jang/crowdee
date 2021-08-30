package team.crowdee.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import team.crowdee.domain.Member;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FundingServiceTest {

    @Test
    public void 인스턴스_테스트() throws Exception {
        //given
        Member member1 = Member.builder()
                .email("mail")
                .nickName("hi")
                .userName("장태환")
                .build();
        Member member2 = Member.builder()
                .email("mail2")
                .nickName("hi2")
                .userName("장태환2")
                .build();
        Member member3 = Member.builder()
                .email("mail3")
                .nickName("hi3")
                .userName("장태환3")
                .build();
        List<Member> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member2);
        //when
        boolean result = memberList.contains(member2);
        //then
        Assertions.assertThat(result).isEqualTo(false);
    }
}