package com.prototype.organisation.contactUs;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	@Value("${email.to}")
	private String emailTo;
	
	@PutMapping
	public HttpStatus sendEmail(@RequestBody ContactUsForm form, HttpServletResponse response) throws MessagingException {

		Properties prop = new Properties();
		
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		
		Session session = Session.getInstance(prop);
		
		Message msg = prepareMessage(session, myAccountEmail, form);
		
		if(msg==null) {
			System.out.println("fail");
			response.setStatus(400);
			return HttpStatus.BAD_REQUEST;
		}
		try {
			Transport.send(msg,myAccountEmail,password);
			response.setStatus(200);
			return HttpStatus.ACCEPTED;
		}catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
			return HttpStatus.EXPECTATION_FAILED;
		}
	}
	
	private Message prepareMessage(Session session, String myAccountEmail, ContactUsForm form) {
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(myAccountEmail, form.getName() + "(" + form.getEmail() + ")"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
			msg.setSubject(form.getSubject());
			msg.setText(form.getMessage());
			return msg;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
