package com.docmgmt.struts.actions.repository_OLD;


import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class CreateRepository extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		
		destWorkspaceDetails = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		
		//sourceWorkspaceDetails = docMgmtImpl.getUserWorkspace(userGroupCode, userId);		
		
		// change by Virendra Barad for Get All Source Workspace user wise in Repository menu
		sourceWorkspaceDetails = docMgmtImpl.getUserWorkspace(userGroupCode,userId);
		return SUCCESS;
	}
	
	public Vector destWorkspaceDetails;
	public Vector sourceWorkspaceDetails;

	public Vector getDestWorkspaceDetails() {
		return destWorkspaceDetails;
	}
	public void setDestWorkspaceDetails(Vector destWorkspaceDetails) {
		this.destWorkspaceDetails = destWorkspaceDetails;
	}
	public Vector getSourceWorkspaceDetails() {
		return sourceWorkspaceDetails;
	}
	public void setSourceWorkspaceDetails(Vector sourceWorkspaceDetails) {
		this.sourceWorkspaceDetails = sourceWorkspaceDetails;
	}
}
	