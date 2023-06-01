package com.docmgmt.struts.actions.template;

import java.util.Vector;


import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditStructure extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	Vector TemplateDetails=new Vector();
	
	@Override
	public String execute()
	{
		TemplateDetails=docMgmtImpl.getAllTemplates();

		return SUCCESS;
	}
	
	

	public Vector getTemplateDetails() {
		return TemplateDetails;
	}

	public void setTemplateDetails(Vector templateDetails) {
		TemplateDetails = templateDetails;
	}
	
	

}
