package team.crowdee.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.dto.FundingViewDTO;
import team.crowdee.domain.dto.MemberDTO;

import javax.mail.MessagingException;

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

        FundingViewDTO fundingViewDTO = new FundingViewDTO();
        fundingViewDTO.setTitle("한솔맨의 일상");
        fundingViewDTO.setThumbNailUrl("naver.com");
        fundingViewDTO.setProjectUrl("test");

//        mimeEmailService.joinFundingMail(memberDTO, fundingDTO);

    }

    @Test
    @Rollback(value = false)
    void 펀딩참여_성공안내() throws MessagingException {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("유한솔");
        memberDTO.setPassword("Abcd12345!@");
        memberDTO.setEmail("yhansol145@naver.com");

        FundingViewDTO fundingViewDTO = new FundingViewDTO();
        fundingViewDTO.setTitle("한솔맨의 일상");
        fundingViewDTO.setThumbNailUrl("naver.com");
        fundingViewDTO.setProjectUrl("test");

        mimeEmailService.successFundingMail(memberDTO, fundingViewDTO);

    }

    @Test
    @Rollback(value = false)
    void 펀딩참여_실패안내() throws MessagingException {

        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserName("유한솔");
        memberDTO.setPassword("Abcd12345!@");
        memberDTO.setEmail("yhansol145@naver.com");

        FundingViewDTO fundingViewDTO = new FundingViewDTO();
        fundingViewDTO.setTitle("한솔맨의 일상");
        fundingViewDTO.setThumbNailUrl("naver.com");
        fundingViewDTO.setProjectUrl("test");

        mimeEmailService.failFundingMail(memberDTO, fundingViewDTO);

    }
}