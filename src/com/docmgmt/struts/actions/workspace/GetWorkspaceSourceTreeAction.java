package com.docmgmt.struts.actions.workspace;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ActionContext;
import com.docmgmt.server.webinterface.beans.CopyWorkspaceTreeBean;
public class GetWorkspaceSourceTreeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		CopyWorkspaceTreeBean treeBean = new CopyWorkspaceTreeBean();
		treeBean.setUserType(userTypeCode);
		htmlCode = treeBean.checkIfFilePresent(sorceWorkspaceId,userGroupCode,userCode);
		
		return SUCCESS;
	}
	
	public String sorceWorkspaceId;
//	public int nodeId;
	public String htmlCode;

	
	public String getSorceWorkspaceId() {
		return sorceWorkspaceId;
	}
	public void setSorceWorkspaceId(String sorceWorkspaceId) {
		this.sorceWorkspaceId = sorceWorkspaceId;
	}

	
}
	
	


