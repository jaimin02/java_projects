package com.docmgmt.struts.actions.master.project;




import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

public class EditProjectAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
	
		return SUCCESS;
		
	}
	
	public String projectCode;
	public String projectName;
	
	
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