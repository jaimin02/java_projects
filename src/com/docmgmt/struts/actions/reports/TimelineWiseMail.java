package com.docmgmt.struts.actions.reports;

import java.util.ArrayList;
import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.docmgmt.dto.DTOTimelineWorkspaceUserRightsMst;
import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.services.mail.MailService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TimelineWiseMail extends ActionSupport {
	private static final long serialVersionUID = 1L;
	
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String htmlContent="";
	public String workspaceId;
	public ArrayList<DTOTimelineWorkspaceUserRightsMst> getProjectDetail;
	ArrayList<DTOWorkSpaceNodeHistory> tempHistory;
	public String workspaceDesc;
	String emailId="";
	String ccUsers="";
	String toEmailId="";
	String html;	
	
	
	public String execute(){
	{				
		StringBuffer siteTableHtml = new StringBuffer();
		MailService mail = new MailService();	
		
		String senderUser="KnowledgeNET";
		String url=ActionContext.getContext().getSession().get("URL").toString();
		System.out.println(url);
		int index=url.lastIndexOf('/');
		String redirectURL = url.substring(0,index) +"/Login_input.do";
	    System.out.println(redirectURL);
		
		getProjectDetail = docMgmtImpl.getProjectStageDetail();
		
		for(int i=0;i<getProjectDetail.size();i++){
			
		workspaceId = getProjectDetail.get(i).getWorkSpaceId();
		workspaceDesc= getProjectDetail.get(i).getWorkspaceDesc();
		
		int nodeId = getProjectDetail.get(i).getNodeId();
		int userCode = getProjectDetail.get(i).getUserCode();
		String userName= getProjectDetail.get(i).getUserName();
		
		String fileName = getProjectDetail.get(i).getFileName();
		
		siteTableHtml.append("<span style=\"font-size:10.5pt;font-family: Times New Roman, serif;\">&nbsp;<b>Dear "+userName+"</span></b>,<br><br>");
		siteTableHtml.append("&nbsp <span float: left; style=\"font-size:10.0pt;font-family: Times New Roman, serif;\">&nbsp;&nbsp;<u>Please find details as below</u>:</span>");
		siteTableHtml.append("<br><br><TABLE style=\"table-layout:fixed; font-family: Times New Roman, serif;border-collapse: collapse; width: 100%;\">");
		
		siteTableHtml.append("<TR>");
		siteTableHtml.append("<TD style=\"text-align: left; padding: 6px; width: 120px;\"><b>Project Name:</b></TD>");
		siteTableHtml.append("<TD style=\"text-align: left; padding: 6px; width: 20px;\">:</TD>");
		siteTableHtml.append("<TD style=\"word-wrap:break-word;\">");
		siteTableHtml.append(workspaceDesc);		
		siteTableHtml.append("</TD></TR>");
	
		siteTableHtml.append("<TR>");
		siteTableHtml.append("<TD style=\"text-align: left; padding: 6px; width: 120px;\"><b>File Name:</b></TD>");
		siteTableHtml.append("<TD style=\"text-align: left; padding: 6px;\">:</TD>");
		siteTableHtml.append("<TD style=\"word-wrap:break-word;\">");
		siteTableHtml.append(fileName);			
		siteTableHtml.append("</TD></TR>");
	
		
		siteTableHtml.append("</TABLE>");
		siteTableHtml.append("<br><br>&nbsp <span style=\"font-size:10.0pt;font-family: Times New Roman, serif;\"><a href=\""+redirectURL+"\" target=\"_blank\"><u style=\"float: left; color:black\">Click here to proceed</u></a></span>");
		siteTableHtml.append("&nbsp <br><br><span style=\"font-size:10.0pt;font-family: Times New Roman, serif;\">Thanks & Regards,</span>");
		siteTableHtml.append("<br><span style=\"font-size:10.5pt;font-family: Times New Roman, serif;\"><b>"+senderUser+"</span></b>");
		//siteTableHtml.append("<span style=\"border-bottom: solid gray 2.25pt;padding: 0.75pt .75pt .75pt .75pt;width: 700px;height: 24px;position: absolute;margin-left: -60px;margin-top: 4px;\"></span>");
	
		System.out.println(siteTableHtml);
	
		html= siteTableHtml.toString();
		
		//System.out.println(html);
				
		Vector<DTOWorkSpaceUserRightsMst> getAttachedUserList = new Vector<DTOWorkSpaceUserRightsMst>();
		getAttachedUserList = docMgmtImpl.getAttachedUserList(workspaceId,nodeId,userCode);
		
		DTOUserMst getUserDetail = docMgmtImpl.getUserByCode(userCode);
		
		emailId = getUserDetail.getLoginName();
		//emailId = "KnowledgeNET@sarjen.com";
				
		System.out.println(emailId);
		//ccUsers = "KnowledgeNET@sarjen.com,virendra.barad@sarjen.com";
		for(int j=0;j<getAttachedUserList.size();j++){
			ccUsers += getAttachedUserList.get(j).getLoginName();
			ccUsers+=",";
		}
		
		if(!ccUsers.isEmpty() || !ccUsers.equals("")){
			ccUsers = ccUsers.substring(0, ccUsers.length() - 1);
		}
		
		System.out.println(ccUsers);
		if(!emailId.isEmpty() || !emailId.equals("")){
	
			new Thread(new Runnable() {
				public void run() {
					try {
						mail.sendEmail(emailId, ccUsers, null, "Timeline Project Report : "
								+ workspaceDesc, html, null, true);
					} catch (AddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		}
		else{
			System.out.println("Please Enter E-mail Address...");
		}
		siteTableHtml.delete(0, siteTableHtml.length());
	}
	}
	return "html";
}
}
