package team.crowdee.controller;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Creator;
import team.crowdee.domain.Follow;
import team.crowdee.domain.Grade;
import team.crowdee.domain.Member;
import team.crowdee.repository.MemberRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberControllerTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager em;

    @Test
    public void 크리에이터테스트() throws Exception {
        //given

        Member member5 = memberRepository.findById(5L);

        Creator creator1 = Creator.builder()
                .BusinessNumber("123-1231-1231")
                .build();
        memberRepository.saveCreator(creator1);
        member5.joinCreator(creator1);
        System.out.println(member5.getUserName());
        em.flush();
        em.clear();
        //when
        Member findMember = memberRepository.findById(5L);
        Creator findCreator = findMember.getCreator();
        //then
        System.out.println("findCreator.getBusinessNumber() = " + findCreator.getBusinessNumber());
        assertThat(findCreator).isEqualTo(creator1);
    }

    @Test
    @Rollback(value = false)
    public void 더미데이터() {
        for (int i = 0; i < 10; i++) {
            memberRepository.save(
                Member.builder()
                    .birth("1992/" + i)
                    .nickName("테스트" + i)
                    .email("mail" + i + "@mail.com")
                    .userName("user" + i)
                    .postDate(LocalDateTime.now())
                    .age(20 + i)
                    .userId("testId" + i)
                    .gender("남자")
                    .mobile("010-1234-123" + i)
                    .grade(Grade.BRONZE)
                    .build()
            );
        }
    }

}