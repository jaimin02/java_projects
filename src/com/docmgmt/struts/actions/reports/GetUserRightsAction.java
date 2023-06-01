package com.docmgmt.struts.actions.reports;

import java.util.Vector;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.DocMgmtImpl;

public class GetUserRightsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
		
		getAllWorkSpace = docMgmtImpl.getWorkspaceDetailByProjectType();
		
		return SUCCESS;
	}
	
	
	public Vector getAllWorkSpace;

	public Vector getGetAllWorkSpace() {
		return getAllWorkSpace;
	}


	public void setGetAllWorkSpace(Vector getAllWorkSpace) {
		this.getAllWorkSpace = getAllWorkSpace;
	}
	
	
}	
