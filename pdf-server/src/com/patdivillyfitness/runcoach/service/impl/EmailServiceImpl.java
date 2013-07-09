package com.patdivillyfitness.runcoach.service.impl;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.patdivillyfitness.runcoach.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Override
	public boolean sendPlainMail(String body, InternetAddress toAdd, String subject ){
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("d.lyons88@gmail.com", "Social Transformation (Darren Lyons)"));
            msg.addRecipient(Message.RecipientType.TO,
                             toAdd);
            msg.setSubject(subject);
            msg.setText(body);
            Transport.send(msg);
    
        } catch (Exception e) {
            // ...
        	return false;
        }        
        return true;
	}

}
