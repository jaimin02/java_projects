package com.docmgmt.struts.actions.labelandpublish;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.docmgmt.dto.DTOSubmissionMst;
import com.docmgmt.dto.DTOSubmittedWorkspaceNodeDetail;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceNodeDetail;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UpdateCompleteSubmssion extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workspaceId;
	
	public String applicationNo;
	
	public int labelno;
	
	public String SubmissionId;
	
	public DTOSubmissionMst submision; 
	
	@Override
	public String execute()
	{
		
		
	
		DTOSubmissionMst dto=new DTOSubmissionMst();
		
		/*dto.setWorkspaceId(workspaceId);
		dto.setApplicationNo(applicationNo);
		dto.setLabelNo(labelno);
		dto.setSubmittedBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		dto.setLastPublishedVersion(dto1.getLastPublishedVersion());
		*/
		
		submision=docMgmtImpl.getSubmissionDetail(SubmissionId);

		System.out.println("LabelNo: " + labelno);
		
		DTOSubmittedWorkspaceNodeDetail dtosub = new DTOSubmittedWorkspaceNodeDetail();
		DTOWorkSpaceNodeDetail dtownd = new DTOWorkSpaceNodeDetail();
		
		
		Vector data = new Vector();
		data= docMgmtImpl.getNodeForRevisedSubmission(workspaceId,labelno);
		System.out.println("size of else vector :::"+data.size());

		docMgmtImpl.updatePublishedVersion(workspaceId);
		DTOWorkSpaceMst dto1 = docMgmtImpl.getWorkSpaceDetail(workspaceId);
		

		submision.setSubmissionId(SubmissionId);
		submision.setSubmittedBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		submision.setLastPublishedVersion(dto1.getLastPublishedVersion());
		docMgmtImpl.insertSubmissionMst(submision,2); //Update Published Version in SubmissionMst
		
		dtosub.setWorkspaceId(workspaceId);
		dtosub.setSubmissionId(SubmissionId);
		dtosub.setLastPublishVersion(dto1.getLastPublishedVersion());
		ArrayList<DTOSubmittedWorkspaceNodeDetail> subNodeDtlLst = new ArrayList<DTOSubmittedWorkspaceNodeDetail>();
		if(data.size()>0)
		{
			for (Iterator iterator = data.iterator(); iterator.hasNext();) {
				DTOWorkSpaceNodeDetail nDtl = (DTOWorkSpaceNodeDetail) iterator.next();
				DTOSubmittedWorkspaceNodeDetail dtoSubNodeDtl = new DTOSubmittedWorkspaceNodeDetail();
				dtoSubNodeDtl.setNodeId(nDtl.getNodeId());
				dtoSubNodeDtl.setIndexId("node-"+nDtl.getNodeId());//Setting the index value as per the ID attribute value of LEAF element is given in the publish logic.
				dtoSubNodeDtl.setWorkspaceId(workspaceId);
				dtoSubNodeDtl.setSubmissionId(SubmissionId);
				dtoSubNodeDtl.setLastPublishVersion(dto1.getLastPublishedVersion());
				subNodeDtlLst.add(dtoSubNodeDtl); 
			}
			docMgmtImpl.insertIntoSubmittedWorkspaceNodeDetail(subNodeDtlLst);
			 
		}
		
		/* insert value into submittedWorkspaceNodeDetail  */
		
		/*if(labelno == 1)
		{
			 Vector data= docMgmtImpl.getDetailOfSubmission(workspaceId,labelno);
			 dtosub.setWorkspaceId(workspaceId);
			 dtosub.setSubmissionId(SubmissionId);
			 dtosub.setLastPublishVersion(dto1.getLastPublishedVersion());
			 if(data.size()>0)
			 {	 
				 docMgmtImpl.insertIntoSubmittedWorkspaceNodeDetail(data,dtosub);
			 }
		}
		else
		{
			 Vector data= docMgmtImpl.getNodeForRevisedSubmission(workspaceId,labelno);
			 System.out.println("size of else vector :::"+data.size());
			 dtosub.setWorkspaceId(workspaceId);
			 dtosub.setSubmissionId(SubmissionId);
			 dtosub.setLastPublishVersion(dto1.getLastPublishedVersion());
			 if(data.size()>0)
			 {
				 docMgmtImpl.insertIntoSubmittedWorkspaceNodeDetail(data,dtosub);
				 
			 }
		}*/
		
		
		return SUCCESS;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getSubmissionId() {
		return SubmissionId;
	}

	public void setSubmissionId(String submissionId) {
		SubmissionId = submissionId;
	}

	public DTOSubmissionMst getSubmision() {
		return submision;
	}

	public void setSubmision(DTOSubmissionMst submision) {
		this.submision = submision;
	}

	
}
