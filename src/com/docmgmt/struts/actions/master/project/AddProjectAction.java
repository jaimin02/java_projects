package com.docmgmt.struts.actions.master.project;


import java.util.*;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

public class AddProjectAction extends ActionSupport implements Preparable  {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		
		return SUCCESS;
	}
	
	public  void prepare()throws Exception{
		projectDetail=docMgmtImpl.getProjectType();
	}
	
	public String projectName;
	public Vector projectDetail;
	
	public Vector getProjectDetail() {
		return projectDetail;
	}

	public void setProjectDetail(Vector projectDetail) {
		this.projectDetail = projectDetail;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}