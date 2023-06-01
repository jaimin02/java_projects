package com.docmgmt.struts.actions.reports;

import java.util.Vector;
import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.server.db.DocMgmtImpl;

public class ShowUserDetailAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
			
		userDtl = docMgmtImpl.getUserDetailByWorkspaceId(wsId);
		userDtl.removeElementAt(0);
			
		return SUCCESS;
	}
	
	public Vector userDtl;
	public String wsId;

	public Vector getUserDtl() {
		return userDtl;
	}
	public void setUserDtl(Vector userDtl) {
		this.userDtl = userDtl;
	}
	public String getWsId() {
		return wsId;
	}
	public void setWsId(String wsId) {
		this.wsId = wsId;
	}
	

}
	
	


