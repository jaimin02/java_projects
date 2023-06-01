package com.docmgmt.struts.actions.master.project;

import com.docmgmt.dto.DTOProjectMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveProjectAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		DTOProjectMst dto = new DTOProjectMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark("remark");
		dto.setModifyBy(userId);
	    dto.setProjectName(projectName.trim());
		boolean projectExist = docMgmtImpl.projectNameExist(projectName.trim());
	    if(!projectExist)
	    	docMgmtImpl.ProjectMstOp(dto,1,false);
	    else
	    	addActionError("Project Type Already Exist");
	    return SUCCESS;
	}
	
	
	public String UpdateProject()
	{
		DTOProjectMst dto =docMgmtImpl.getProjectInfo(projectCode);
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setProjectName(projectName.trim());
		dto.setModifyBy(userId);
		boolean projectExist = docMgmtImpl.projectNameExist(projectName.trim());
	    if(!projectExist)
	    {
	    	docMgmtImpl.ProjectMstOp(dto, 2, false);
	    }
	    return SUCCESS;
	}
	
	public String projectName;
	public String projectCode;

	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}