package com.docmgmt.struts.actions.repository_OLD;


import com.docmgmt.server.webinterface.beans.RepositoryCopyWorkspaceTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class RepositoryGetWorkspaceSourceTreeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		RepositoryCopyWorkspaceTreeBean treeBean = new RepositoryCopyWorkspaceTreeBean();
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
	
	


