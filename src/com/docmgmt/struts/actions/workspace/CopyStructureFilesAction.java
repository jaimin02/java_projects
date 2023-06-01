package com.docmgmt.struts.actions.workspace;



import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;


public class CopyStructureFilesAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute(){
	
		docMgmtImpl.copyPublishedStructureToWorkspace(structNo,workSpaceId,userId);

		return SUCCESS;
	}
	

	public String structNo;
	public String workSpaceId;
	public int userId;
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}

	public String getStructNo() {
		return structNo;
	}

	public void setStructNo(String structNo) {
		this.structNo = structNo;
	}

 	
}
