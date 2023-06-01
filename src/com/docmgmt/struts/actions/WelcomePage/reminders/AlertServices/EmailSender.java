package com.docmgmt.struts.actions.WelcomePage.reminders.AlertServices;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.docmgmt.server.prop.KnetProperties;

public class EmailSender {
	private Session session;
	private Properties props;
	private KnetProperties knetProp;
	
	public void mailAuth()
	{
		knetProp = KnetProperties.getPropInfo("Alert.properties");
		props = new Properties();
    	props.put("mail.smtp.host", knetProp.getValue("Email_Host_Name"));
    	props.put("mail.smtp.socketFactory.port", knetProp.getValue("Email_Port"));
    	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
    	props.put("mail.smtp.auth", knetProp.getValue("Email_Auth"));
    	props.put("mail.smtp.port", knetProp.getValue("Email_Port"));
    	//props.put("mail.debug", "true");
    	props.put("mail.smtp.starttls.enable", knetProp.getValue("Email_Starttls"));
		session = Session.getDefaultInstance(props,new javax.mail.Authenticator() 
		{	@Override
		protected PasswordAuthentication getPasswordAuthentication()
			{
				KnetProperties prop = KnetProperties.getPropInfo("Alert.properties");
				return new PasswordAuthentication(prop.getValue("Email_From"),prop.getValue("Email_Password"));	
			}
		});	
	}
	
	public void sendMail(int userCode,String mailBody,String mailSubject,String mailTo)
	{
    	try 
    	{
	        Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress(knetProp.getValue("Email_From")));
		    message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailTo));
		    message.setSubject(mailSubject);
		    Multipart mp = new MimeMultipart();
		    BodyPart bodyPart = new MimeBodyPart();
		    bodyPart.setContent(mailBody, "text/html");
		    mp.addBodyPart(bodyPart);
		    message.setContent(mp);
		    Transport.send(message);
		    mailBody = null;
		    System.out.println("Done");
    	} 
    	catch (MessagingException e) 
    	{
    	    e.printStackTrace();
    	}
	}
}
