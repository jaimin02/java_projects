package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.ProjectNodeWiseMail;
import com.docmgmt.server.webinterface.services.mail.MailService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	public class ProjectNodeWiseMailAction extends ActionSupport {

		private static final long serialVersionUID = 1L;

		public String workSpaceId;
		public String workspaceDesc;
		public String htmlContent="";
		public boolean isTableView;
		public int nodeId;
		public int attrId;
		public int mode=0;	
		String emailId="";
		String ccUsers="";
		public String html="";
		DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
		MailService mail = new MailService();	
		
		@Override
		public String execute(){
			
			if(workSpaceId != null && !workSpaceId.equals("") && !workSpaceId.equals("-1")){
				isTableView=true;
				int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
				int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
				short reportType = ProjectNodeWiseMail.TABLE_HTML;
				htmlContent ="<div style=\"width:100%;\">"+
					 			 "<div style=\" float:left\" align=\"left\">" +
						 			 "<img src=\"images/jquery_tree_img/exp.jpg\" title=\"Show All Node's Attributes\" onclick=\"showHideAllAttributesTable('show');\">  " +
						 			 "<img src=\"images/jquery_tree_img/colaps.jpg\" title=\"Hide All Node's Attributes\" onclick=\"showHideAllAttributesTable('hide');\">" +
					 			 "</div>"+
					 			 "<div style=\"float:right\" align=\"right\">" +
					 			 	"<img alt=\"Table View\" title=\"Table View\" src=\"images/Common/Table.png\" onclick=\"getProjectSnapshot('TableView');\">" +
					 			 "</div>"+
				 			 "</div><br/><br/>";
				if(isTableView){
					reportType = ProjectNodeWiseMail.TABLE_HTML;
					
			/*		htmlContent = 
							"<div style=\"width:100%; height:0; \" align=\"right\"> " +
								"<form id=\"myform\" action=\"ExportToXls.do\" method=\"post\" >"+
									"<input type=\"hidden\" name=\"fileName\" value=\"Project_Tracking.xls\">"+
									"<textarea rows=\"1\"  cols=\"1\" name=\"tabText\" id=\"tableTextArea\" style=\"margin-top: -70px; visibility: hidden;height: 10px;\"></textarea>"+
									"<img alt=\"Export To Excel\" style=\"margin-top: -70px; \" title = \"Export To Excel\" src=\"images/Common/Excel.png\" onclick=\"document.getElementById('tableTextArea').value=document.getElementById('divTabText').innerHTML;document.getElementById('myform').submit()\">&nbsp;"+
								"</form>" +
							"</div>";	*/					
				}
				
				ProjectNodeWiseMail ProjectTackingSnapshotBean = new ProjectNodeWiseMail();
				htmlContent += "<div id=\"divTabText\" style=\"width:100%\">";
				String htmlContentTemp = ProjectTackingSnapshotBean.getWorkspaceHtml(workSpaceId, userGroupCode, userCode,reportType,mode);
				
				if(htmlContentTemp== null || htmlContentTemp.equals("")){
					htmlContentTemp = "No Details Found";
				}
				htmlContent += htmlContentTemp;
				htmlContent += "</div>";
			}
			
			System.out.println(htmlContent);
			
			Vector<DTOWorkSpaceUserRightsMst> getAttachedUserList = new Vector<DTOWorkSpaceUserRightsMst>();
			/*getAttachedUserList = docMgmtImpl.getAttachedUserList(workSpaceId);
			
			for(int j=0;j<getAttachedUserList.size();j++){
				emailId += getAttachedUserList.get(j).getLoginName();
				emailId+=",";
			}*/
		
			if(!emailId.isEmpty() || !emailId.equals("")){
				emailId = emailId.substring(0, emailId.length() - 1);
			}	
			System.out.println(emailId);
		
			if(!emailId.isEmpty() || !emailId.equals("")){
		
				new Thread(new Runnable() {
					public void run() {
						try {
							mail.sendEmail(emailId, null, null, "Assign Documents: "
									+ workspaceDesc, htmlContent, null, true);
						} catch (AddressException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}).start();
				html = "true";
			}
			else{
				System.out.println("Please Enter E-mail Address...");
				html = "false";
			}
			return SUCCESS;
		}
		
	}

