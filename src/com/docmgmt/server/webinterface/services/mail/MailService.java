package com.docmgmt.server.webinterface.services.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.docmgmt.server.prop.PropertyInfo;
import com.docmgmt.server.webinterface.services.CryptoLibrary;

public class MailService {
	Properties properties;
	static PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
	CryptoLibrary decryptObj = new CryptoLibrary();
	String EmailPass = propertyInfo.getValue("EmailPassword") ;
	private static final String from = propertyInfo.getValue("EmailFromAddress");
	
	Session session;

	public MailService() {
		properties = new Properties();
		/*properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");*/
		
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.office365.com");
		properties.put("mail.smtp.port", "587");
		String pass = decryptObj.decrypt(EmailPass);
		
		session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, pass);
					}
				});
	}

	public static void main(String[] args) throws AddressException,
			MessagingException {
		String toList = from;
		MailService mail = new MailService();
		String body = "<body><table border='2'><tr><td>Test</td><td>Test1</td></tr></table></body>";
		mail.sendEmail(toList, null, null, "Query Managment", body, null, true);

	}

	public void sendEmail(String toList, String ccList, String bccList,
			String subject, String body, String attachment, boolean isHTML)
			throws AddressException, MessagingException {

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipients(Message.RecipientType.TO, InternetAddress
				.parse(toList));
		if (ccList != null) {
			message.addRecipients(Message.RecipientType.CC, InternetAddress
					.parse(ccList));
		}
		if (bccList != null) {
			message.addRecipients(Message.RecipientType.BCC, InternetAddress
					.parse(bccList));

		}

		message.setSubject(subject);
		if (attachment != null) {
			BodyPart messageBodyPart = new MimeBodyPart();
			if (isHTML)
				messageBodyPart.setContent(body, "text/html; charset=utf-8");
			else
				message.setText(body);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			messageBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(attachment);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachment);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

		} else {
			if (isHTML)
				message.setContent(body, "text/html; charset=utf-8");
			else
				message.setText(body);
		}
		try {
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}
		
	}
}