package team.crowdee.util;

import apitest.test.domain.MailDTO;
import apitest.test.domain.Member;
import apitest.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.crowdee.domain.dto.MailDTO;
import team.crowdee.repository.MemberRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender jms;
    private static final String SEND_EMAIL_ADDRESS = "Crowdee[크라우디]";

    public MailDTO createMailAndChangePass(String email, String userName) {
        String str = getTempPass();
        log.info("tempPassword={}", str);
        MailDTO dto = new MailDTO();
        dto.setEmail(email);
        dto.setTitle(userName + " 님의 임시번호 안내 이메일입니다");
        dto.setMessage("안녕하세요. [Crowdee] 임시 비밀번호 안내 이메일 입니다.  </br> " + "[ " + userName + " ] 님의 임시 비밀번호는 [" +
                str + "] 입니다. 로그인 후 꼭 변경해주세요");
        updatePassword(str, email);
        return dto;
    }

    private void updatePassword(String str, String email) {
        Member findMember = memberRepository.findByEmail(email);
        String encryptPass = passwordEncoder.encode(str);
        log.info("encryptPass={}", encryptPass);
        findMember.changePass(encryptPass);
    }

    public void sendMail(MailDTO mailDTO) {
        log.info("이메일 전송 완료 to={}", mailDTO.getEmail());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getEmail());
        message.setFrom(SendEmailService.SEND_EMAIL_ADDRESS);
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());

        jms.send(message);

    }


    private String getTempPass() {
        UUID uuid = UUID.randomUUID();
        String temp = uuid.toString();
        return temp;
    }


}
