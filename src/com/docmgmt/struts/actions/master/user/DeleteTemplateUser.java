package com.docmgmt.struts.actions.master.user;

import com.docmgmt.dto.DTOTemplateUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteTemplateUser extends ActionSupport
{
	public String templateId;
	public String userId;
	public String userGroupId;
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTOTemplateUserMst objtmp=new DTOTemplateUserMst();
		objtmp.setTemplateId(templateId);
		objtmp.setUserCode(Integer.parseInt(userId));
		objtmp.setUserGroupCode(Integer.parseInt(userGroupId));
	
		DTOTemplateUserMst obj =docMgmtImpl.getUserDetails(objtmp);
		
		obj.setTemplateId(templateId);
		obj.setUserCode(Integer.parseInt(userId));
		obj.setUserGroupCode(Integer.parseInt(userGroupId));
		int ucode=Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		obj.setModifyBy(ucode);
		
		docMgmtImpl.inActiveUserFromTemplate(obj);
		
		addActionMessage(obj.getUsername() +" is Deleted from the template.");
		
		return SUCCESS;
	}
	
		
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserGroupId() {
		return userGroupId;
	}
	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
	
}
