package com.docmgmt.struts.actions.workspace;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
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
 
class MailSender {
	private String mailHost="smtp.gmail.com";
	private String body;
	private String myFile="F:\\DRacing.avi";
 
	private Properties props;
	private Session mailSession;
	
 
	private MimeMessage message;
	private InternetAddress sender;
 
	private Multipart mailBody;
	private MimeBodyPart mainBody;
	private MimeBodyPart mimeAttach;
 
	private DataSource fds;
 
 
	MailSender()
	{
		//Creating a Session
		props=new Properties();
				props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.auth", "true");
 
		mailSession=Session.getDefaultInstance(props, new MyAuthenticator());
 
		
		//Constructing and Sending a Message
		try
		{
			//Starting a new Message
			message=new MimeMessage(mailSession);
			mailBody=new MimeMultipart();
 
			//Setting the Sender and Recipients
			sender=new InternetAddress("dider7@gmail.com", "Kayes");
			message.setFrom(sender);
 
			InternetAddress[] toList={new InternetAddress("thegreendove@yahoo.com")};
			
 
			message.setRecipients(Message.RecipientType.TO, toList);
			
			//Setting the Subject and Headers
			message.setSubject("My first JavaMail program");
 
			//Setting the Message body
			body="Hello!";
			mainBody=new MimeBodyPart();
			mainBody.setDataHandler(new DataHandler(body, "text/plain"));
			mailBody.addBodyPart(mainBody);
 
			//Adding a single attachment
			fds=new FileDataSource(myFile);
			mimeAttach=new MimeBodyPart();
			mimeAttach.setDataHandler(new DataHandler(fds));
			mimeAttach.setFileName(fds.getName());
			mailBody.addBodyPart(mimeAttach);
 
			message.setContent(mailBody);
 
						Transport.send(message);
		}
		catch(java.io.UnsupportedEncodingException e)
		{
			System.out.println(e);
		}
		catch(MessagingException e)
		{
			System.out.println(e);
		}
		catch(IllegalStateException e)
		{
			System.out.println(e);
		}
	}
 
	}
 /*
public class TestMail01
{
	public static void main(String args[])
	{
		new MailSender();
	}
}*/
 
class MyAuthenticator extends Authenticator
{
	MyAuthenticator()
	{
		super();
	}
 
	@Override
	protected PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication("dider7", "MY_PASSWORD");
	}
}
 