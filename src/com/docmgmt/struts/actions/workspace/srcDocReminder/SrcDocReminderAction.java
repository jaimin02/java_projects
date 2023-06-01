package com.docmgmt.struts.actions.workspace.srcDocReminder;

import java.util.ArrayList;
import java.util.HashMap;

import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.prop.KnetProperties;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SrcDocReminderAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ArrayList<HashMap<String, Object>> subqueryMsg = new ArrayList<HashMap<String, Object>>();
	public DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public ArrayList<DTOWorkSpaceNodeHistory>  getSrcDocReminder;
	public int documentCount=0;
	public String htmlContent;
	public String lbl_folderName;
	public String lbl_nodeName;
	KnetProperties knetProperties = KnetProperties.getPropInfo();

	@Override
	public String execute() {

		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());	
		lbl_folderName = knetProperties.getValue("ForlderName");
		lbl_nodeName = knetProperties.getValue("NodeName");		
		getSrcDocReminder = docMgmtImpl.getSrcDocReminderList(userCode,userGroupCode);
		
		return SUCCESS;
	}
	
	public String SrcDocCount(){
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		getSrcDocReminder = docMgmtImpl.getSrcDocReminderList(userCode,userGroupCode);
		
		if(getSrcDocReminder.size()>0){
			documentCount = getSrcDocReminder.size();
		}
		htmlContent=Integer.valueOf(documentCount).toString();
		//return Integer.valueOf(commentCount).toString();
		return "html";
	}

}
