package com.docmgmt.struts.actions.replication;

import java.util.Vector;



import com.opensymphony.xwork2.ActionSupport;


import com.docmgmt.server.db.DocMgmtImpl;

import com.opensymphony.xwork2.ActionContext;

public class ReplicationNodeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
	
		return SUCCESS;
	}
	
	public Vector getWorkspaceDetail;

	public Vector getGetWorkspaceDetail() {
		return getWorkspaceDetail;
	}

	public void setGetWorkspaceDetail(Vector getWorkspaceDetail) {
		this.getWorkspaceDetail = getWorkspaceDetail;
	}
	
}