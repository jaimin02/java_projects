package com.docmgmt.struts.actions.master.user;

import java.util.*;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AddUserToTemplateForm extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String workspaceid;
	
	private Date now;
	
	 public Date getNow() {
	        return now;
	    }
	
	
	public Vector getAllTemplates;
	public Vector templateUserGroupsDetails;
//	public Vector templateUserDetailList;
	public Vector getStageDetail;
	
	@Override
	public String execute()
	{
		Calendar cal = Calendar.getInstance();
        now = cal.getTime();
	    templateUserGroupsDetails=docMgmtImpl.getAllUserGroups();
   //   templateUserDetailList=docMgmtImpl.getTemplateUserDetailList(templateId);
		getStageDetail = docMgmtImpl.getStageDetail();
		getAllTemplates = docMgmtImpl.getAllTemplates(); 
		return SUCCESS;
	}


	public Vector getGetAllTemplates() {
		return getAllTemplates;
	}

	public void setGetAllTemplates(Vector getAllTemplates) {
		this.getAllTemplates = getAllTemplates;
	}

	public Vector getTemplateUserGroupsDetails() {
		return templateUserGroupsDetails;
	}
	public void setTemplateUserGroupsDetails(Vector templateUserGroupsDetails) {
		this.templateUserGroupsDetails = templateUserGroupsDetails;
	}

	public Vector getGetStageDetail() {
		return getStageDetail;
	}

	public void setGetStageDetail(Vector getStageDetail) {
		this.getStageDetail = getStageDetail;
	}

}
