package team.crowdee.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.MailDTO;
import team.crowdee.repository.MemberRepository;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MimeEmailService {

    private final JavaMailSender javaMailSender;

    @Async
    public String sendAuthMail(String email) throws MessagingException {
        String authKey = getKey();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String mailContent = "<h1>[이메일 인증]</h1><br><p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>"
                + "<a href='http://localhost:8081/member/signUpConfirm?email="
                + email + "&authKey=" + authKey + "' target='_blank'>이메일 인증 확인</a>";
        mimeMessage.setFrom("Crowdee@gmail.com");
        mimeMessage.setSubject("[Crowdee 회원가입 인증 이메일 입니다.]");
        mimeMessage.setText(mailContent,"UTF-8","html");
        mimeMessage.addRecipients(Message.RecipientType.TO, email);
        javaMailSender.send(mimeMessage);
        return authKey;
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

}
