package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class ShowVersionReportAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		getWorkSpace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		return SUCCESS;
	}
	
	public Vector getWorkSpace;

	public Vector getGetWorkSpace() {
		return getWorkSpace;
	}

	public void setGetWorkSpace(Vector getWorkSpace) {
		this.getWorkSpace = getWorkSpace;
	}
	
	
}
	
	


