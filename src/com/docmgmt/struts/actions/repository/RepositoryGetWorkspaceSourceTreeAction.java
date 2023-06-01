package com.docmgmt.struts.actions.repository;


import com.docmgmt.server.webinterface.beans.JQueryDestinationTreeBean;
import com.docmgmt.server.webinterface.beans.JQuerySourceTreeBean;
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
		//RepositoryCopyWorkspaceTreeBean treeBean = new RepositoryCopyWorkspaceTreeBean();
		JQuerySourceTreeBean srcTreeObj = new JQuerySourceTreeBean();
		
		//srcTreeObj.setUserType(userTypeCode);
		//System.out.println("hi:"+sorceWorkspaceId+":::"+userCode);
		
		//srcTreehtmlCode = srcTreeObj.getWorkspaceTreeHtml(sorceWorkspaceId,userGroupCode,userCode);   comment on 8-5-2015
		

		// added on 8-5-2015 By dharmendra jadav
		//start
		
		srcTreehtmlCode = srcTreeObj.getWorkspaceTreeHtml(sorceWorkspaceId);
		
		//end
		
		System.out.println("Source Workspace Tree:"+srcTreehtmlCode);
		
		JQueryDestinationTreeBean decTreeObj = new JQueryDestinationTreeBean();
		
		//decTreeObj.setUserType(userTypeCode);
		//System.out.println("hi:"+destWorkspaceId+":::"+userCode);  									comment on 8-5-2015
		//decTreehtmlCode = decTreeObj.getWorkspaceTreeHtml(destWorkspaceId,userGroupCode,userCode);
		
		
		// added on 8-5-2015 By dharmendra jadav
		//start
		
		decTreehtmlCode = decTreeObj.getWorkspaceTreeHtml(destWorkspaceId);
		
		//end
		
		System.out.println("Dest Workspace Tree:"+decTreehtmlCode);
		
		
		return SUCCESS;
	}
	
	public String sorceWorkspaceId;
	public String srcTreehtmlCode;
	public String destWorkspaceId;
	public String decTreehtmlCode;
	
	public String getSorceWorkspaceId() {
		return sorceWorkspaceId;
	}
	public void setSorceWorkspaceId(String sorceWorkspaceId) {
		this.sorceWorkspaceId = sorceWorkspaceId;
	}

	
}
	
	


