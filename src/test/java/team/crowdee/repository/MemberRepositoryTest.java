package team.crowdee.repository;

import com.zaxxer.hikari.util.FastList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Follow;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.valuetype.AccountInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

  /*  @Test
    @Rollback(value = false)*/
    public void 테스트_회원저장() throws Exception {
        //given
        //when
        //Long save = memberRepository.save(member);
        for (int i = 0; i < 5; i++) {
            Member member = memberRepository.save(Member.builder()
                    .userName("장태환" + i)
                    .password("1q2w3e4r!"+i)
                    .birth("1992/" + i)
                    .nickName("테스트" + i)
                    .email("mail" + i + "@mail.com")
                    .emailCert("ABCD")
                    .userName("user" + i)
                    .registDate(LocalDateTime.now())
                    .age(20 + i)
                    .userId("testId" + i)
                    .gender("남자")
                    .mobile("010-1234-123" + i)
                    .build());

        }
        //then
        //디비확인완료
    }

    @Test
    public void 테스트_로그인() {
        //given
        String userId = "testId3";
        //when
        Member member = memberRepository.login(userId);
        //then
        assertThat(member).isNotNull();

    }

    @Test
    @Rollback(value = false)
    public void 테스트_한명회원찾기_회원삭제() {
        //given
        Member member = memberRepository.findById(2L);
        //when
 //       memberRepository.delete(member);
        //디비확인완료 삭제된다.
        //then


    }

    @Test
    public void 테스트_파라미터로찾기() {
        List<Member> param = memberRepository.findByParam("userId", "testId2");
        Member member = param.get(0);
        System.out.println(member.getNickName());

    }

    @Test
    public void 테스트_이메일유저아이디로찾기() {
        List<Member> members = memberRepository.findByEmailAndUserId("testId2", "mail2@mail.com");
        Member member = members.get(0);
        System.out.println(member.getBirth());
        assertThat(member).isNotNull();

    }

    @Test
    public void 테스트_이메일찾기확인가져오기() {
        List<Member> toConfirm = memberRepository.findToConfirm("mail2@mail.com", "ABCD");
        Member member = toConfirm.get(0);
        System.out.println(member.getAge());
        assertThat(member).isNotNull();

    }

    @Test
    public void 테스트_전체찾기() {
        List<Member> all = memberRepository.findAll();
        //assertThat(all.size()).isEqualTo(5);
    }


}