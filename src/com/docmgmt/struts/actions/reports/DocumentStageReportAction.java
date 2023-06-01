package com.docmgmt.struts.actions.reports;

import java.util.*;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.DocMgmtImpl;


public class DocumentStageReportAction extends ActionSupport
{

	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		getAllWorkspace = docMgmtImpl.getWorkspaceDetailByProjectType();
		getAllStages = docMgmtImpl.getStageDetail();
		return SUCCESS;
	}
	public Vector getAllWorkspace;
	public Vector getAllStages;

	public Vector getGetAllWorkspace() {
		return getAllWorkspace;
	}
	public void setGetAllWorkspace(Vector getAllWorkspace) {
		this.getAllWorkspace = getAllWorkspace;
	}
	public Vector getGetAllStages() {
		return getAllStages;
	}
	public void setGetAllStages(Vector getAllStages) {
		this.getAllStages = getAllStages;
	}
}
