package com.prototype.organisation.contactUs;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/contact-us")
public class ContactUsController {
	
	@Value("${email.address}")
	private String myAccountEmail;
	@Value("${email.password}")
	private String password;
	
	@PutMapping
	public HttpStatus sendEmail(@RequestBody ContactUsForm form) throws MessagingException {

		Properties prop = new Properties();
		
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(prop);
		
		Message msg = prepareMessage(session, myAccountEmail, form);
		
		Transport.send(msg,myAccountEmail,password);
		return HttpStatus.ACCEPTED;
	}
	
	private Message prepareMessage(Session session, String myAccountEmail, ContactUsForm form) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(myAccountEmail, form.getName() + "(" + form.getEmail() + ")"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(myAccountEmail));
			msg.setSubject(form.getSubject());
			msg.setText(form.getMessage());
			return msg;
		}catch(Exception e) {
			System.out.println("Failed to send email!");
			e.printStackTrace();
			return null;
		}
	}
}
