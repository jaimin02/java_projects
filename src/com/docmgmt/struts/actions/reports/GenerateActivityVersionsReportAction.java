package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.server.db.DocMgmtImpl;

public class GenerateActivityVersionsReportAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
	
		getUserDtl = docMgmtImpl.getNodeDetailForActivityReport(workSpaceId);
	
		return SUCCESS;
	}
	
	public Vector getUserDtl;
	public String workSpaceId;
	
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public Vector getGetUserDtl() {
		return getUserDtl;
	}

	public void setGetUserDtl(Vector getUserDtl) {
		this.getUserDtl = getUserDtl;
	}

	
	
	
}
	
	


