package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;

public class GenerateActivityCommentReportAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		DTOWorkSpaceNodeDetail dto = new DTOWorkSpaceNodeDetail();
    	dto.setWorkspaceId(workSpaceId);
    	dto.setNodeId(nodeId);
    	dto.setUserCode(userId);
    	
    	
		if(reportId==1 || reportId==-1)
		{
			commentsOnProject = docMgmtImpl.getActivityCommentsReport(dto);
    		return "commentreport";
		}
		
		else if(reportId==2)
		{
			commentsOnFiles = docMgmtImpl.getFileVersionAndCommentReport(dto);
    		return "fileversionhistoryreport";
		}
		
		auditTrailDtl = docMgmtImpl.getAuditTrailReport(dto);
		return "audittrailreport";
		
	}
	
	public String workSpaceId;
	public int nodeId;
	public int userId;
	public int reportId;
	public Vector commentsOnProject;
    public Vector commentsOnFiles;
	public Vector auditTrailDtl;

	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
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
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public Vector getCommentsOnProject() {
		return commentsOnProject;
	}
	public void setCommentsOnProject(Vector commentsOnProject) {
		this.commentsOnProject = commentsOnProject;
	}
	public Vector getCommentsOnFiles() {
		return commentsOnFiles;
	}
	public void setCommentsOnFiles(Vector commentsOnFiles) {
		this.commentsOnFiles = commentsOnFiles;
	}
	public Vector getAuditTrailDtl() {
		return auditTrailDtl;
	}
	public void setAuditTrailDtl(Vector auditTrailDtl) {
		this.auditTrailDtl = auditTrailDtl;
	}
	
}