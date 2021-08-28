package team.crowdee.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.FundingDTO;
import team.crowdee.domain.dto.MemberDTO;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MimeEmailServiceTest {

    @Autowired
    MimeEmailService mimeEmailService;

    @Test
    @Rollback(value = false)
    void 펀딩참여_안내메일() throws MessagingException {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("유한솔");
        memberDTO.setPassword("Abcd12345!@");
        memberDTO.setEmail("yhansol145@naver.com");

        FundingDTO fundingDTO = new FundingDTO();
        fundingDTO.setTitle("한솔맨의 일상");
        fundingDTO.setThumbNailUrl("naver.com");
        fundingDTO.setProjectUrl("test");

        mimeEmailService.joinFundingMail(memberDTO, fundingDTO);

    }

    @Test
    @Rollback(value = false)
    void 펀딩참여_성공안내() throws MessagingException {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("유한솔");
        memberDTO.setPassword("Abcd12345!@");
        memberDTO.setEmail("yhansol145@naver.com");

        FundingDTO fundingDTO = new FundingDTO();
        fundingDTO.setTitle("한솔맨의 일상");
        fundingDTO.setThumbNailUrl("naver.com");
        fundingDTO.setProjectUrl("test");

        mimeEmailService.successFundingMail(memberDTO, fundingDTO);

    }

    @Test
    @Rollback(value = false)
    void 펀딩참여_실패안내() throws MessagingException {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("유한솔");
        memberDTO.setPassword("Abcd12345!@");
        memberDTO.setEmail("yhansol145@naver.com");

        FundingDTO fundingDTO = new FundingDTO();
        fundingDTO.setTitle("한솔맨의 일상");
        fundingDTO.setThumbNailUrl("naver.com");
        fundingDTO.setProjectUrl("test");

        mimeEmailService.failFundingMail(memberDTO, fundingDTO);

    }
}