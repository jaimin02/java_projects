package com.docmgmt.struts.actions.workspace;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.PropertyInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SendRemarksToUser extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public int[] userCodedtl;
	
	public int nodeId;
	
	public String message;
	
//	private DataSource fds;
	
	//private Multipart mailBody;
	//private MimeBodyPart mainBody;
	//private MimeBodyPart mimeAttach;
 
	
	@Override
	public String execute()
	{
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		//int usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		String sendEmail = propertyInfo.getValue("SEND_EMAIL");
		
		DTOWorkSpaceMst dtows=docMgmtImpl.getWorkSpaceDetail(workspaceID);
		DTOWorkSpaceNodeDetail dtowsnd = docMgmtImpl.getNodeDetail(workspaceID, nodeId).get(0);
		
		DTOUserMst dtoum=new DTOUserMst();
		String sender = ActionContext.getContext().getSession().get("username").toString();	
		for(int i=0;i<userCodedtl.length;i++)
		{
			
				
			DTOWorkSpaceNodeDetail objWorkSpaceNodeDetail = new DTOWorkSpaceNodeDetail();	
			dtoum=docMgmtImpl.getUserByCode(userCodedtl[i]);
			
			
			objWorkSpaceNodeDetail.setWorkspaceId(workspaceID);
			objWorkSpaceNodeDetail.setNodeId(nodeId);
			objWorkSpaceNodeDetail.setUserGroupCode(dtoum.getUserGroupCode());
			objWorkSpaceNodeDetail.setUserCode(userCodedtl[i]);
			
			String  subjectId =docMgmtImpl.insertForumComment(objWorkSpaceNodeDetail, message ,usercode);

		
			if(sendEmail != null && sendEmail.equalsIgnoreCase("YES"))	{
				 Properties props = new Properties();  
				 props.put("mail.transport.protocol", "smtp");
				 props.put("mail.smtp.host", "smtp.gmail.com");
				 props.put("mail.smtp.port", "25");
				 props.put("mail.smtp.auth", "true");  
		         props.put("mail.debug", "true");  
		         props.put("mail.smtp.starttls.enable","true");

		         Session session = Session.getInstance(props, new MyAuth());  
		   
		         try {  
		             Message msg = new MimeMessage(session);  
		             msg.setFrom(new InternetAddress("nirav.parmar@sarjen.com")); 

		             InternetAddress[] address = {new InternetAddress("nirav.parmar@sarjen.com")};  

		             msg.setRecipients(Message.RecipientType.TO, address);  
		             msg.setSubject("KnowledgeNET ["+ dtows.getWorkSpaceDesc()+"]");  

		             msg.setSentDate(new Date());  
		             msg.setText("Project: "+dtows.getWorkSpaceDesc()+"\nNode\\Document: "+dtowsnd.getNodeDisplayName()+"\nSender : "+sender+"\n\nComment: "+message);  
		            
		           //Adding a single attachment
		 			//fds=new FileDataSource("D:\\Documents\\FOP.ppt");
		 			//mimeAttach=new MimeBodyPart();
		 		//	mimeAttach.setDataHandler(new DataHandler(fds));
		 		//setFileName(fds.getName());
		 		//	mailBody.addBodyPart(mimeAttach);
		        //	msg.setContent(mailBody);
		             System.out.println("Reached -4 ");
		             Transport.send(msg);  
		             System.out.println("Message Sent Successfully ");
		         }  
		         catch (MessagingException e) {e.printStackTrace(); }  
			}
		}
		return SUCCESS;
	}

	public int[] getUserIdDtl() {
		return userCodedtl;
	}

	public void setUserIdDtl(int[] userCodedtl) {
		this.userCodedtl = userCodedtl;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	 class MyAuth extends Authenticator {  
	     @Override
		protected PasswordAuthentication getPasswordAuthentication() {  
	         return new PasswordAuthentication("nirav.parmar@sarjen.com","");  
	     }  
	  
	
}
}
