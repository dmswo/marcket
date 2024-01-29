package carrotMarcket.marcket.global.util;

import carrotMarcket.marcket.board.event.CommentMailEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSender {
    private final JavaMailSender javaMailSender;
    public void sendEmail(CommentMailEvent event) {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(event.getEmail());
            helper.setSubject("Re: ["+event.getTitle()+"] 댓글이 달렸습니다.");
            helper.setText("메일 정상도착");

            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("e.message : {}", e.getMessage());
        }
    }
}
