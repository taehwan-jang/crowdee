package team.crowdee.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import team.crowdee.domain.Funding;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.RejectionDTO;
import team.crowdee.domain.dto.FundingViewDTO;
import team.crowdee.domain.dto.MemberDTO;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public String sendAllBackerToSuccessMail(Set<Member> memberList, Funding funding) throws MessagingException{
        String emailList = Utils.appendMemberEmail(memberList);
        MimeMessage successMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                "<div style='text-align:center;'>" +
                    "<h1>[펀딩 성공!]</h1><br> 회원님께서 참여하신 "+ funding.getTitle() + " 펀딩이 100% 달성되었습니다." +
                    "<p><img src='"+ funding.getThumbNailUrl()+"'/></p>" +
                    "<p>참여펀딩 바로가기 : http://localhost:3000/contents/" + funding.getProjectUrl()+ "</p>" +
                    "<br><p>결제는 <h3>마이페이지</h3> -> <h3>결제요청 프로젝트</h3>에서 진행해주세요.</p>" +
//                    "결제URL : " + " http://https://github.com/taehwan-jang/crowdee" +
                    "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                "</div>";
        successMessage.setSubject("[Crowdee] " + funding.getTitle() + " 펀딩이 100% 달성에 성공하였습니다.");
        successMessage.setFrom("Crowdeefunding@gmail.com");
        successMessage.setText(mailContent, "UTF-8", "html");
        successMessage.setRecipients(Message.RecipientType.TO, emailList);
//        successMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(memberDTO.getEmail()));
        javaMailSender.send(successMessage);

        return null;
    }

    // 펀딩 100% 미달성 시 발송되는 메일
    @Async
    public String sendAllBackerToFailMail(Set<Member> memberList, Funding funding) throws MessagingException{
        String emailList = Utils.appendMemberEmail(memberList);
        MimeMessage failMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                "<div style='text-align:center;'>" +
                    "<h1>[펀딩 실패]</h1><br>회원님 께서 참여하신 " + funding.getTitle() + " 펀딩이 미달성되었습니다." +
                    "<br><p>안타깝게도 참여하신 펀딩이 100% 모금에 실패했습니다. <br/></p>" +
                    "<br><p>기다리시던 크리에이님의 공연을 보여드리지 못해 아쉬움이 큰만큼 <br/></p>" +
                    "<br><p>더욱 더 좋은 서비스와 공연을 제공해드릴 수 있도록 지원하겠습니다.<br/></p>" +
                    "<br><p>다음 만남을 기약하며 너른 양해 부탁드립니다. 감사합니다.<br/></p>" +
                    "<p><img src='"+ funding.getThumbNailUrl()+"'/></p>" +
                    "<h3>Crowdee 펀딩에 참여해주셔서 감사합니다.</h3>" +
                "</div>";
        failMessage.setSubject("[Crowdee] " + funding.getTitle() + " 펀딩이 100% 달성에 실패하였습니다.");
        failMessage.setFrom("Crowdeefunding@gmail.com");
        failMessage.setText(mailContent, "UTF-8", "html");
        failMessage.setRecipients(Message.RecipientType.TO,emailList);
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
    public String rejectCreatorMail(Member member, RejectionDTO rejectionDTO) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[크리에이터 심사 부적합]</h1><br>" + member.getUserName() + "님의 크리에이터 승인이 거절 되었습니다." +
                        "<p>사유:" + rejectionDTO.getMailContents() + "</p>" +
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
    public String rejectFundingMail(Member member, Funding funding, RejectionDTO rejectionDTO) throws MessagingException{

        MimeMessage rejectMessage = javaMailSender.createMimeMessage();
        String mailContent =
                "<img style='width:300px; display:block; margin-left:auto; margin-right:auto;' src= 'http://localhost:8081/api/image/img/crowdeeImg.png' />" +
                        "<div style='text-align:center;'>" +
                        "<h1>[펀딩 심사 부적합]</h1><br>" + member.getUserName() + "님 께서 참여하신 "+ funding.getTitle() + " 펀딩 승인이 거절 되었습니다." +
                        "<p><img style='width:300px;' src='"+funding.getThumbNailUrl()+"'/></p>" +
                        "<p>사유:" + rejectionDTO.getMailContents() + "</p>" +
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
