package com.docmgmt.struts.actions.master.user;

import com.docmgmt.dto.DTOTemplateUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditTemplateUser extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	public String templateId;
	public String userId;
	public String userGroupId;
	
	public DTOTemplateUserMst getTemplateUserDetail = new DTOTemplateUserMst(); 
	

	
	public DTOTemplateUserMst getGetTemplateUserDetail() {
		return getTemplateUserDetail;
	}

	public void setGetTemplateUserDetail(DTOTemplateUserMst getTemplateUserDetail) {
		this.getTemplateUserDetail = getTemplateUserDetail;
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


	@Override
	public String execute()
	{
		DTOTemplateUserMst dto=new DTOTemplateUserMst();
		dto.setTemplateId(templateId);
		dto.setUserCode(Integer.parseInt(userId));
		dto.setUserGroupCode(Integer.parseInt(userGroupId));
	
		getTemplateUserDetail=docMgmtImpl.getUserDetails(dto);
		return SUCCESS;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
