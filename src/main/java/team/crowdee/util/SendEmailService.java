package team.crowdee.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.crowdee.domain.Member;
import team.crowdee.domain.dto.MailDTO;
import team.crowdee.repository.MemberRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender jms;
    private static final String SEND_EMAIL_ADDRESS = "Crowdee";

    public MailDTO createMailAndChangePass(String email, String userName) {
        String str = getTempPass();
        MailDTO dto = new MailDTO();
        dto.setEmail(email);
        dto.setTitle(userName + " 님의 임시번호 안내 이메일입니다");
        dto.setMessage("안녕하세요. [Crowdee] 임시 비밀번호 안내 이메일 입니다. " + "[ " + userName + " ] 님의 임시 비밀번호는 [" +
                str + "] 입니다. 로그인 후 꼭 변경해주세요");
        updatePassword(str, email);
        return dto;
    }

    @Transactional
    public void updatePassword(String str, String email) {
        List<Member> findMember = memberRepository.findByParam("email", email);
        String encryptPass = passwordEncoder.encode(str);
        findMember.get(0).changePassword(encryptPass);
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
