package com.docmgmt.struts.actions.template;

import com.docmgmt.dto.DTOTemplateMst;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

import java.util.Vector;

public class DeleteTemplateAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userid = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		
		Vector templateDetail =docMgmtImpl.getTemplateDetailById(templateId);
		DTOTemplateMst dto = (DTOTemplateMst)templateDetail.get(0);
		
		
		if(dto.getStatusIndi() == 'D')
		{
			dto.setStatusIndi('E');
		}
		else
		{
			dto.setStatusIndi('D');
		}
		
		dto.setModifyBy(userid);
		
		docMgmtImpl.updateTemplateMst(dto);
	    return SUCCESS;
	}	
	
	public String templateId;


	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
   
	
	
	/**********************************************/
	
	
	
}
