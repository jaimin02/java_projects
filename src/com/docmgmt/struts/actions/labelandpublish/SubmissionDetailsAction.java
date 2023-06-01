package com.docmgmt.struts.actions.labelandpublish;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.server.db.DocMgmtImpl;

public class SubmissionDetailsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		submissionDetail = docMgmtImpl.getSubmissionDetail(submissionId);
		return SUCCESS;
	}
	
	public DTOSubmissionMst submissionDetail=new DTOSubmissionMst();
	public String submissionId;

	
	public String getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(String submissionId) {
		this.submissionId = submissionId;
	}

	
	public DTOSubmissionMst getSubmissionDetail() {
		return submissionDetail;
	}

	public void setSubmissionDetail(DTOSubmissionMst submissionDetail) {
		this.submissionDetail = submissionDetail;
	}

	
	
	
}
	
	


