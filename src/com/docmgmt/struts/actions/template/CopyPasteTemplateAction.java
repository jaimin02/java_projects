package com.docmgmt.struts.actions.template;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class CopyPasteTemplateAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		sourceTemplateDetails = docMgmtImpl.getAllTemplates();
		destTemplateDetails = docMgmtImpl.getAllTemplates();
	
		return SUCCESS;
	}	
	
	public Vector destTemplateDetails;
	public Vector sourceTemplateDetails;


	public Vector getDestTemplateDetails() {
		return destTemplateDetails;
	}
	public void setDestTemplateDetails(Vector destTemplateDetails) {
		this.destTemplateDetails = destTemplateDetails;
	}
	public Vector getSourceTemplateDetails() {
		return sourceTemplateDetails;
	}
	public void setSourceTemplateDetails(Vector sourceTemplateDetails) {
		this.sourceTemplateDetails = sourceTemplateDetails;
	}
	
	
}
