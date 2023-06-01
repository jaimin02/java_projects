package com.docmgmt.struts.actions.repository;



import com.docmgmt.server.webinterface.beans.JQueryDestinationTreeBean;
import com.opensymphony.xwork2.ActionSupport;

public class RepositoryGetWorkspaceDestTreeAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	
	@Override
	public String execute()
	{
		
		//int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		//int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		//String userTypeCode = ActionContext.getContext().getSession().get("usertypecode").toString();
		
		JQueryDestinationTreeBean destTreeObj = new JQueryDestinationTreeBean();
		//destTreeObj.setUserType(userTypeCode);    comment on 8-5-2015
		
		// added on 8-5-2015 By dharmendra jadav
		//start
		
		decTreehtmlCode = destTreeObj.getWorkspaceTreeHtml(destWorkspaceId);
		
		//end
		
		System.out.println("Dest Workspace Tree123:"+decTreehtmlCode);
		//destTreeStream = new ByteArrayInputStream(decTreehtmlCode.getBytes()); 
		return SUCCESS;
	}
	
	public String destWorkspaceId;
	public String decTreehtmlCode;
	//public InputStream destTreeStream;
	

	
	public String getDestWorkspaceId() {
		return destWorkspaceId;
	}
	public void setDestWorkspaceId(String destWorkspaceId) {
		this.destWorkspaceId = destWorkspaceId;
	}
	
	public String getDecTreehtmlCode() {
		return decTreehtmlCode;
	}
	
	public void setDecTreehtmlCode(String decTreehtmlCode) {
		this.decTreehtmlCode = decTreehtmlCode;
	}
	
	/*public InputStream getDestTreeStream() {
		return destTreeStream;
	}*/
	
	
	
}
	
	


