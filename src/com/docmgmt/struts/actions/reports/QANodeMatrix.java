package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class QANodeMatrix extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	

	@Override
	public String execute()
	{
		setTemplateId("0039");
		
		return SUCCESS;
	}
	public String templateId;	
	public Vector workSpaceId;
	public int nodeId;
	public int userId;
	public String sortOn;
	public String sortBy;
	public Vector getUserRightsReportDtl;
	
	public String getTemplateId()
	{
		return templateId;
	}
	public void setTemplateId(String vTemplateID){
		this.templateId=vTemplateID;
	}
	
	public Vector getWorkSpaceId() {
		return workSpaceId;
	}
	
	//0039
	public void setWorkSpaceId(Vector workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSortOn() {
		return sortOn;
	}
	public void setSortOn(String sortOn) {
		this.sortOn = sortOn;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public Vector getGetUserRightsReportDtl() {
		return getUserRightsReportDtl;
	}
	public void setGetUserRightsReportDtl(Vector getUserRightsReportDtl) {
		this.getUserRightsReportDtl = getUserRightsReportDtl;
	}
	
}	
