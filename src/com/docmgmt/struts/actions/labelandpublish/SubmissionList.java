package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class SubmissionList extends ActionSupport{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();


	 public Vector SubmissionDetail;
	 public String relatedSeqNumber;
	// public DTOWorkSpaceMst workspace;
	 public String workSpaceId;
	 
	 @Override
	public String execute()
	 {
		 
		// workspace=docMgmtImpl.getWorkSpaceDetail(workSpaceId);
		 SubmissionDetail=docMgmtImpl.getAllSubmissionDetail(workSpaceId);
		 return SUCCESS;
	 }
	 
	 	public Vector getSubmissionDetail() {
	 		return SubmissionDetail;
	 	}	

		public void setSubmissionDetail(Vector submissionDetail) {
			SubmissionDetail = submissionDetail;
		}

		public String getRelatedSeqNumber() {
			return relatedSeqNumber;
		}

		public void setRelatedSeqNumber(String relatedSeqNumber) {
			this.relatedSeqNumber = relatedSeqNumber;
		}

		public String getWorkSpaceId() {
			return workSpaceId;
		}

		public void setWorkSpaceId(String workSpaceId) {
			this.workSpaceId = workSpaceId;
		}

	 
}
