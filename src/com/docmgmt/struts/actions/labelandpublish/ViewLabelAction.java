package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.server.db.DocMgmtImpl;

public class ViewLabelAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		
		getViewDetail  =  docMgmtImpl.viewLabelUsingWorkspaceId(workSpaceId);
		
		return SUCCESS;
	}
	
	public Vector getViewDetail;
	public String workSpaceId;
	
	public Vector getGetViewDetail() {
		return getViewDetail;
	}

	public void setGetViewDetail(Vector getViewDetail) {
		this.getViewDetail = getViewDetail;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	
}
	
	


