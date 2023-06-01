package com.docmgmt.struts.actions.labelandpublish;

import com.docmgmt.dto.DTOInternalLabelMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveLabelAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		boolean exists = docMgmtImpl.labelNameExist(workSpaceId, labelId);
		
		if(exists)
		{
			addActionError("Label Id Already Exists...");
			
			return "exists";
		}
		
		DTOInternalLabelMst dto = new DTOInternalLabelMst();
		dto.setWorkspaceId(workSpaceId);
		dto.setLabelId(labelId);
		dto.setRemark(labelReamak);
		dto.setModifyBy(userId);
		docMgmtImpl.createInternalLabel(dto);
		return SUCCESS;
	}
	
	public String workSpaceId;
	public String labelReamak;
	public String labelId;
	public String workSpaceDesc;

	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	
	
}
	
	


