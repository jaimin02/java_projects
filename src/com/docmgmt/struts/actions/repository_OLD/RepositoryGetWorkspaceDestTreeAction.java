package com.docmgmt.struts.actions.repository_OLD;



import com.docmgmt.server.db.DocMgmtImpl;
import com.docmgmt.server.webinterface.beans.RepositoryPasteWorkspaceTreeBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
public class RepositoryGetWorkspaceDestTreeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		RepositoryPasteWorkspaceTreeBean treeBean = new RepositoryPasteWorkspaceTreeBean();
		treeBean.setUserType(userTypeCode);
		htmlCode = treeBean.checkIfFilePresent(destWorkspaceId,userGroupCode,userCode);
		return SUCCESS;
	}
	
	public String destWorkspaceId;
	public String htmlCode;
	

	
	public String getHtmlCode() {
		return htmlCode;
	}
	public void setHtmlCode(String htmlCode) {
		this.htmlCode = htmlCode;
	}
	public String getDestWorkspaceId() {
		return destWorkspaceId;
	}
	public void setDestWorkspaceId(String destWorkspaceId) {
		this.destWorkspaceId = destWorkspaceId;
	}
	
	
	
}
	
	


