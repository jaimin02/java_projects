package com.docmgmt.struts.actions.labelandpublish;

import java.util.Vector;

import com.docmgmt.dto.DTOAttributeMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.dto.DTOWorkSpaceUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetAllWorkspace extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workSpaceId;
	public String workSpaceDesc;
	public String userTypeName;
	public int attrId;
	public String wsId;
	public String htmlContent;
	public String selectedWsId;
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		userTypeName = ActionContext.getContext().getSession().get("usertypename").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}
		getWorkspaceDetail = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		return SUCCESS;
	}

public String UserOnProjectDtlHistory(){
        
	workspaceUserHistory = docMgmtImpl.getWorkspaceUserHistoryForReport(selectedWsId);
	workSpaceDesc=workspaceUserHistory.get(0).getWorkspacedesc();
	
	return SUCCESS;
}	
	public Vector<DTOWorkSpaceMst> getWorkspaceDetail;
	public Vector<DTOAttributeMst> getSelectedAttrDetail;
	public Vector<DTOWorkSpaceUserMst> workspaceUserHistory;
	
	public Vector<DTOWorkSpaceMst> getGetWorkspaceDetail() {
		return getWorkspaceDetail;
	}

	public void setGetWorkspaceDetail(Vector<DTOWorkSpaceMst> getWorkspaceDetail) {
		this.getWorkspaceDetail = getWorkspaceDetail;
	}

	public Vector<DTOAttributeMst> getGetSelectedAttrDetail() {
		return getSelectedAttrDetail;
	}

	public void setGetSelectedAttrDetail(Vector<DTOAttributeMst> getSelectedAttrDetail) {
		this.getSelectedAttrDetail = getSelectedAttrDetail;
	}

	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	
	
}
	
	


