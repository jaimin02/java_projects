package com.docmgmt.struts.actions.template;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditStructureAction extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	Vector TemplateDetails=new Vector();
	
	public String templateId;
	
	
	
	@Override
	public String execute()
	{
		TemplateDetails=docMgmtImpl.getAllTemplates();

		return SUCCESS;
	}



	public String getTemplateId() {
		return templateId;
	}



	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
}
