package com.docmgmt.struts.actions.labelandpublish.USsubmission;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkspaceApplicationDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class WorkspaceApplicationAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	public String htmlContent;
	public int applicationId;
	
	public Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetailList;
	
	public String workSpaceId;
	
	public String submissionId;
	public String sequenceNumber;
	public String applicationNumber;
	
	public char isMainApplication;
	
	Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetaileVector;
	
	@Override
	public String execute()
	{
				
		
		workspaceApplicationDetailList=docMgmtImpl.getWorkspaceApplicationDetail(workSpaceId);
		
		return SUCCESS;
	}
	
	public String save() 
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOWorkspaceApplicationDetail dto = new DTOWorkspaceApplicationDetail();
	
		dto.setWorkspaceId(workSpaceId);
		dto.setSubmissionId(submissionId);
		dto.setSequenceNumber(sequenceNumber);
		dto.setApplicationNumber(applicationNumber);
		dto.setIsMainApplication(isMainApplication);
		dto.setModifyBy(userId);
		
		System.out.println("--->>" + dto.getApplicationId()+ dto.getWorkspaceId());
		System.out.println("--->>" + dto.getSubmissionId()+ dto.getSequenceNumber());
		System.out.println("--->>" + dto.getModifyOn()+ dto.getModifyBy());
		System.out.println("--->>" + dto.getIsMainApplication() + dto.getStatusIndi());
		System.out.print( "-->" +applicationNumber);
		
		
		docMgmtImpl.insertWorkspaceApplicationDetail(dto, 1);//'insert' Mode
		htmlContent = "Workspace Application Details are Added Successfully";
		return "html";
	}
	
	
	public String remove()
	{
		DTOWorkspaceApplicationDetail dto = new DTOWorkspaceApplicationDetail();
		
		dto.setApplicationId(applicationId);
		System.out.println("in remove applicationId " + dto.getApplicationId());
		
		docMgmtImpl.insertWorkspaceApplicationDetail(dto, 3);//'delete' Mode
		
		htmlContent = "Workspace Application Details Removed Successfully";
		return "html";
	}
	
	public String updateDetails()
	{
		DTOWorkspaceApplicationDetail dto = new DTOWorkspaceApplicationDetail();
		dto.setApplicationId(applicationId);
		dto.setSubmissionId(submissionId);
		dto.setSequenceNumber(sequenceNumber);
		dto.setApplicationNumber(applicationNumber);
		
		
		System.out.println("I m in updateDetails");
		System.out.println("Application ID--"+dto.getApplicationId()+ "Submission Id--"+ dto.getSubmissionId());
		
		docMgmtImpl.insertWorkspaceApplicationDetail(dto, 2);//'update' tracking number Mode
		htmlContent="Workspace Application Details are updated successfully.";
		return  "html";
	}

	
	
	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	
	public DocMgmtImpl getDocMgmtImpl() {
		return docMgmtImpl;
	}

	public void setDocMgmtImpl(DocMgmtImpl docMgmtImpl) {
		this.docMgmtImpl = docMgmtImpl;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public Vector<DTOWorkspaceApplicationDetail> getWorkspaceApplicationDetailList() {
		return workspaceApplicationDetailList;
	}

	public void setWorkspaceApplicationDetailList(
			Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetailList) {
		this.workspaceApplicationDetailList = workspaceApplicationDetailList;
	}

	public String getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}

	

	public char getIsMainApplication() {
		return isMainApplication;
	}

	public void setIsMainApplication(char isMainApplication) {
		this.isMainApplication = isMainApplication;
	}

	public Vector<DTOWorkspaceApplicationDetail> getWorkspaceApplicationDetaileVector() {
		return workspaceApplicationDetaileVector;
	}

	public void setWorkspaceApplicationDetaileVector(
			Vector<DTOWorkspaceApplicationDetail> workspaceApplicationDetaileVector) {
		this.workspaceApplicationDetaileVector = workspaceApplicationDetaileVector;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
