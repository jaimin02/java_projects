package com.docmgmt.struts.actions.master.project;


import com.docmgmt.dto.DTOProjectMst;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;



public class DeleteProjectAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTOProjectMst dto = docMgmtImpl.getProjectInfo(projectCode);
		
		docMgmtImpl.ProjectMstOp(dto, 2,true);
	    return SUCCESS;
	}
	
	public String statusIndi;
	public String projectCode;


	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	
	/**********************************************/
	
}
