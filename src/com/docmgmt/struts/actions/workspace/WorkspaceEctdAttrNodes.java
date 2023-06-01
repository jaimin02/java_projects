package com.docmgmt.struts.actions.workspace;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.webinterface.beans.WorkspaceEctdAttributeTree;
import java.util.*;


public class WorkspaceEctdAttrNodes extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String htmlCode;
	
	public String client_name;
	
	public String project_type;
	
	public String project_name;
	
	
	
	@Override
	public String execute()
	{
		WorkspaceEctdAttributeTree tree=new WorkspaceEctdAttributeTree();
		
		String workspaceID=ActionContext.getContext().getSession().get("ws_id").toString();
		int usergrpcode=Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		int usercode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		String userType=ActionContext.getContext().getSession().get("usertypecode").toString();
		
		tree.setUserType(userType);
		htmlCode = tree.checkIfFilePresent(workspaceID,usergrpcode,usercode);
		
		Vector wsDetail = docMgmtImpl.getWorkspaceDesc(workspaceID);
		
		if(wsDetail != null){
			Object[] record = new Object[wsDetail.size()];
			record = (Object[])wsDetail.elementAt(0);
			if(record != null)
			{ 
				client_name=record[4].toString();
				
				project_type=record[5].toString();
				
				project_name=record[1].toString();
				
			}
		}
		return SUCCESS;
	}

	public String getHtmlCode() {
		return htmlCode;
	}

	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getProject_type() {
		return project_type;
	}

	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
}
