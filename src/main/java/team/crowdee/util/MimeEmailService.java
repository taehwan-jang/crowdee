package team.crowdee.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.CreatorRejectDTO;
import team.crowdee.domain.dto.FundingRejectDTO;
import team.crowdee.domain.dto.FundingViewDTO;
import team.crowdee.domain.dto.MemberDTO;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class MimeEmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public String sendAuthMail(String email) throws MessagingException {
        String authKey = getKey();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String mailContent = "<h1>[이메일 인증]</h1><br><p>아래 코드를 가입창에 입력해주세요.</p>"
                + email + "<h3>" + authKey + "</h3>";
        mimeMessage.setFrom("Crowdeefunding@gmail.com");
        mimeMessage.setSubject("[Crowdee 회원가입 인증 이메일 입니다.]");
        mimeMessage.setText(mailContent,"UTF-8","html");
        mimeMessage.addRecipients(Message.RecipientType.TO, email);
        javaMailSender.send(mimeMessage);
        return authKey;
    }

    // 펀딩 참여 완료 시 발송되는 메일
    @Async
     public String joinFundingMail(Member member, Funding funding) throws MessagingException{

        MimeMessage joinMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                 "<div style='text-align:center;'>" +
                    "<h1>[펀딩 참여 완료]</h1><br>" + member.getUserName() + "님 께서 참여하신 "+ funding.getTitle() + " 펀딩이 정상적으로 참여 완료되었습니다." +
                    "<p><img style='width:300px;' src='"+funding.getThumbNailUrl()+"'/></p>" +
                    "<p>펀딩 목표금액이 달성되거나, 펀딩 종료 시 추가로 안내메일 발송됩니다.</p>" +
                    "<p>참여펀딩 바로가기 : http://localhost:8081/contents/" + funding.getProjectUrl()+ "</p>" +
                    "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                "<br></div>";

        joinMessage.setSubject("[Crowdee] " + funding.getTitle() + " 펀딩 참여가 완료되었습니다.");
        joinMessage.setFrom("Crowdee.funding@gmail.com");
        joinMessage.setText(mailContent, "UTF-8", "html");
        joinMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
        javaMailSender.send(joinMessage);

        return null;
    }

    // 펀딩 100% 달성 시 발송되는 메일
    @Async
    public String successFundingMail(MemberDTO memberDTO, FundingViewDTO fundingViewDTO) throws MessagingException{

        MimeMessage successMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                "<div style='text-align:center;'>" +
                    "<h1>[펀딩 성공!]</h1><br>" + memberDTO.getUserName() + "님 께서 참여하신 "+ fundingViewDTO.getTitle() + " 펀딩이 100% 달성되었습니다." +
                    "<p><img src='"+ fundingViewDTO.getThumbNailUrl()+"'/></p>" +
                    "<p>참여펀딩 바로가기 : http://localhost:8081/contents/" + fundingViewDTO.getProjectUrl()+ "</p>" +
                    "<br><p>아래 링크를 클릭하여 결제를 진행해주시기 바랍니다.</p>" +
                    "결제URL : " + " http://https://github.com/taehwan-jang/crowdee" +
                    "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                "</div>";
        successMessage.setSubject("[Crowdee] " + fundingViewDTO.getTitle() + " 펀딩이 100% 달성에 성공하였습니다.");
        successMessage.setFrom("Crowdeefunding@gmail.com");
        successMessage.setText(mailContent, "UTF-8", "html");
        successMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(memberDTO.getEmail()));
        javaMailSender.send(successMessage);

        return null;
    }

    // 펀딩 100% 미달성 시 발송되는 메일
    @Async
    public String failFundingMail(MemberDTO memberDTO, FundingViewDTO fundingViewDTO) throws MessagingException{

        MimeMessage failMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                "<div style='text-align:center;'>" +
                    "<h1>[펀딩 실패! ㅋㅋ]</h1><br>" + memberDTO.getUserName() + "님 께서 참여하신 " + fundingViewDTO.getTitle() + " 펀딩이 미달성되었습니다." +
                    "<br><p>진행중인 펀딩 보러가기</p>" +
                    "<p><img src='"+ fundingViewDTO.getThumbNailUrl()+"'/></p>" +
                    "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                "</div>";
        failMessage.setSubject("[Crowdee] " + fundingViewDTO.getTitle() + " 펀딩이 100% 달성에 실패하였습니다.");
        failMessage.setFrom("Crowdeefunding@gmail.com");
        failMessage.setText(mailContent, "UTF-8", "html");
        failMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(memberDTO.getEmail()));
        javaMailSender.send(failMessage);

        return null;
    }

    private String getKey() {
        StringBuffer buffer = new StringBuffer();
        char[] chars =
                {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'N', 'M', 'O', 'P', 'Q', 'S', 'T', 'U', 'W', 'X', 'Y', 'Z'};
        for (int i = 0; i < 10; i++) {
            buffer.append(chars[(int) (Math.random() * chars.length)]);
        }
        return buffer.toString();
    }

    //크리에이터거절(프론트랑 같이 테스트했을때되면 OK)
    @Async
    public String rejectCreatorMail(Member member, CreatorRejectDTO creatorRejectDTO) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[크리에이터 심사 부적합]</h1><br>" + member.getUserName() + "님의 크리에이터 승인이 거절 되었습니다." +
                        "<p>사유:" + creatorRejectDTO.getReason() + "</p>" +
                        "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                        "<br></div>";

        rejectMessage.setSubject("[Crowdee] " + member.getUserName() + " 님의 크리에이터 신청이 거절되었습니다.");
        rejectMessage.setFrom("Crowdee.funding@gmail.com");
        rejectMessage.setText(mailContent, "UTF-8", "html");
        rejectMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
        javaMailSender.send(rejectMessage);

        return null;
    }

    //펀딩거절(프론트랑 같이 테스트했을때되면 OK)
    @Async
    public String rejectFundingMail(Member member, Funding funding, FundingRejectDTO fundingRejectDTO) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[펀딩 심사 부적합]</h1><br>" + member.getUserName() + "님 께서 참여하신 "+ funding.getTitle() + " 펀딩 승인이 거절 되었습니다." +
                        "<p><img style='width:300px;' src='"+funding.getThumbNailUrl()+"'/></p>" +
                        "<p>사유:" + fundingRejectDTO.getReason() + "</p>" +
                        "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                        "<br></div>";

        rejectMessage.setSubject("[Crowdee] " + funding.getTitle() + " 펀딩 신청이 거절되었습니다.");
        rejectMessage.setFrom("Crowdee.funding@gmail.com");
        rejectMessage.setText(mailContent, "UTF-8", "html");
        rejectMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
        javaMailSender.send(rejectMessage);

        return null;
    }






    //////////////////아래는이메일테스트용




    //크리에이터거절 이메일 테스트용
    @Async
    public String rejectFundingMailTest(Member member) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[펀딩 심사 부적합]</h1><br>" + member.getUserName() + "님의 크리에이터 승인이 거절 되었습니다." +
                        "<p>사유:호로로ㅗ롤ㄹ입니다...</p>" +
                        "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                        "<br></div>";

        rejectMessage.setSubject("[Crowdee] " + member.getUserName() + " 크리에이터 신청이 거절되었습니다.");
        rejectMessage.setFrom("Crowdee.funding@gmail.com");
        rejectMessage.setText(mailContent, "UTF-8", "html");
        rejectMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
        javaMailSender.send(rejectMessage);

        return null;
    }

    //펀딩거절 이메일 테스트용
    @Async
    public String rejectFundingMailTest(Member member, Funding funding) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[펀딩 심사 부적합]</h1><br>" + member.getUserName() + "님 께서 참여하신 "+ funding.getTitle() + " 펀딩 승인이 거절 되었습니다." +
                        "<p><img style='width:300px;' src='"+funding.getThumbNailUrl()+"'/></p>" +
                        "<p>사유:호로로ㅗ롤ㄹ입니다...</p>" +
                        "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                        "<br></div>";

        rejectMessage.setSubject("[Crowdee] " + funding.getTitle() + " 펀딩 신청이 거절되었습니다.");
        rejectMessage.setFrom("Crowdee.funding@gmail.com");
        rejectMessage.setText(mailContent, "UTF-8", "html");
        rejectMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(member.getEmail()));
        javaMailSender.send(rejectMessage);

        return null;
    }

}
