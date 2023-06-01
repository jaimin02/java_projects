package com.docmgmt.struts.actions.reports;

import java.util.Vector;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.DocMgmtImpl;


public class GetAllNodesUserRightsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
		
	
		getNodeDetail = docMgmtImpl.getNodeDetailForActivityReport(workSpaceId);
		getUserDtl= docMgmtImpl.getUserDetailByWorkspaceId(workSpaceId);
		
		return SUCCESS;
	}
	
	public Vector getNodeDetail;
	public Vector getUserDtl;
	public String workSpaceId;
	
	
	public Vector getGetUserDtl() {
		return getUserDtl;
	}

	public void setGetUserDtl(Vector getUserDtl) {
		this.getUserDtl = getUserDtl;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public Vector getGetNodeDetail() {
		return getNodeDetail;
	}

	public void setGetNodeDetail(Vector getNodeDetail) {
		this.getNodeDetail = getNodeDetail;
	}
	
	
}	
