package ewhamenu.com.demo.service;

import ewhamenu.com.demo.controller.MainhomeController;
import ewhamenu.com.demo.domain.Email;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String sender_address = "univ.menu.edaejang@gmail.com";
    private static final Logger logger = LoggerFactory.getLogger(MainhomeController.class);

    public MailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    public void emailSend(Email mail){
//        mail.setAddress(userEmail);
//        mail.setMessage(userId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
        message.setFrom(MailService.sender_address);
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());
//        logger.info("id : {} ", mail.getAddress());
        mailSender.send(message);
    }
}
