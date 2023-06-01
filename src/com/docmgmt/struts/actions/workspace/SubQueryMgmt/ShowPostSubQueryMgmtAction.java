package com.docmgmt.struts.actions.workspace.SubQueryMgmt;

import java.util.Vector;

import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ShowPostSubQueryMgmtAction extends ActionSupport {

	public Vector<DTOWorkSpaceMst> workspaces;
	public String workSpaceId;
	DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public String mi;
	
	@Override
	public String execute(){
		
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workSpaceId = defaultWorkSpaceId;
		}
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		workspaces = docMgmtImpl.getUserWorkspace(userGroupCode, userCode);
		//Workspaces with at least one sequence confirmed, remove otherwise.
		for (int itrWsMst = 0; itrWsMst < workspaces.size(); itrWsMst++) 
		{
			DTOWorkSpaceMst dtoWsMst = workspaces.get(itrWsMst);
			if (dtoWsMst.getLastPublishedVersion().equals("-999")) {
				workspaces.remove(itrWsMst);
				itrWsMst--;
			}
		}
		return SUCCESS;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}

	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
}
