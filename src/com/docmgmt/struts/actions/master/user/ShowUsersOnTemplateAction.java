package com.docmgmt.struts.actions.master.user;

import java.util.*;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class ShowUsersOnTemplateAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	

	
	private Date now;
	
	 public Date getNow() {
	        return now;
	    }
	
	public Vector templateUserDetailList;
	public String templateId;
	
	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	@Override
	public String execute()
	{
		Calendar cal = Calendar.getInstance();
        now = cal.getTime();
        templateUserDetailList=docMgmtImpl.getTemplateUserDetailList(templateId);
     	return SUCCESS;
	}

	public Vector getTemplateUserDetailList() {
		return templateUserDetailList;
	}

	public void setTemplateUserDetailList(Vector templateUserDetailList) {
		this.templateUserDetailList = templateUserDetailList;
	}


	
}
