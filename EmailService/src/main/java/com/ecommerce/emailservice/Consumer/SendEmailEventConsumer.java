package com.ecommerce.emailservice.Consumer;

import com.ecommerce.emailservice.DTO.SendEmailEventDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class SendEmailEventConsumer {

    ObjectMapper objectMapper;

    public SendEmailEventConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "sendEmail",groupId = "emailService")
    public void sendEmailEventConsumer(String message) throws JsonProcessingException {

            SendEmailEventDto event = objectMapper.readValue(message,SendEmailEventDto.class);
            String to = event.getTo();
            String body = event.getBody();
            String subject = event.getSubject();

        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("apache.kafka.emailservice@gmail.com", "wtesqnvealottceb");
            }
        };
        Session session = Session.getInstance(props, auth);

        EmailUtil.sendEmail(session, to,subject, body);

    }
    }

