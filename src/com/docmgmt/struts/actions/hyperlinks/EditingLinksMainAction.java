package com.docmgmt.struts.actions.hyperlinks;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class EditingLinksMainAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public String workspaceid;
	
	@Override
	public String execute(){
		
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String defaultWorkSpaceId = ActionContext.getContext().getSession().get("defaultWorkspace").toString();
		if(defaultWorkSpaceId != null && !defaultWorkSpaceId.equals("")){
			workspaceid = defaultWorkSpaceId;
		}
		getAllWorkSpace = docMgmtImpl.getUserWorkspace(userGroupCode, userId);
		
		return SUCCESS;
	}
	
	
	public Vector getAllWorkSpace;

	public Vector getGetAllWorkSpace() {
		return getAllWorkSpace;
	}


	public void setGetAllWorkSpace(Vector getAllWorkSpace) {
		this.getAllWorkSpace = getAllWorkSpace;
	}


	public String getWorkspaceid() {
		return workspaceid;
	}


	public void setWorkspaceid(String workspaceid) {
		this.workspaceid = workspaceid;
	}
}	
