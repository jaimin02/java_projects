package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.dto.DTOTemplateUserRightsMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ViewTemplateUserRightsAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String templateId;
	public int userCode;
	public Vector getUserRightsReportDtl;	

	@Override
	public String execute()
	{
	
		DTOTemplateUserRightsMst dto = new DTOTemplateUserRightsMst();
		dto.setTemplateId(templateId);
		dto.setUserCode(userCode);
		getUserRightsReportDtl = docMgmtImpl.getTemplateUserRightsReport(dto);
		
		return SUCCESS;
    }

	

	public int getUserCode() {
		return userCode;
	}

	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}

	public Vector getGetUserRightsReportDtl() {
		return getUserRightsReportDtl;
	}

	public void setGetUserRightsReportDtl(Vector getUserRightsReportDtl) {
		this.getUserRightsReportDtl = getUserRightsReportDtl;
	}

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}



}
