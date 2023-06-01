package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ViewWorkspaceUserRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public int userCode;
	public Vector getUserRightsReportDtl;	

	@Override
	public String execute(){
	
		DTOWorkSpaceUserRightsMst dto = new DTOWorkSpaceUserRightsMst();
		dto.setNodeId(0);
		dto.setWorkSpaceId(workSpaceId);
		dto.setUserCode(userCode);
		getUserRightsReportDtl = docMgmtImpl.getUserRightsReport(dto);
		
		return SUCCESS;
    }

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public Vector getGetUserRightsReportDtl() {
		return getUserRightsReportDtl;
	}

	public void setGetUserRightsReportDtl(Vector getUserRightsReportDtl) {
		this.getUserRightsReportDtl = getUserRightsReportDtl;
	}



}
