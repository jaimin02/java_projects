package com.docmgmt.struts.actions.reports;

import java.util.*;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOWorkSpaceNodeHistory;
import com.docmgmt.server.db.DocMgmtImpl;


public class ShowDocumentStageReportAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		DTOWorkSpaceNodeHistory dto = new DTOWorkSpaceNodeHistory();
		dto.setWorkSpaceId(workSpaceId);
		dto.setStageId(stageId);
		dto.setModifyBy(userCode);
	//	stageDetails= docMgmtImpl.documentStageDetails(dto);
		return SUCCESS;
	}
	
	public int stageId;
	public String workSpaceId;
	public int userCode;
	public Vector stageDetails;

	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
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
	public Vector getStageDetails() {
		return stageDetails;
	}
	public void setStageDetails(Vector stageDetails) {
		this.stageDetails = stageDetails;
	}
}