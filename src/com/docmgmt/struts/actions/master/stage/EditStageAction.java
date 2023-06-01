package com.docmgmt.struts.actions.master.stage;

import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;


public class EditStageAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
	    return SUCCESS;
	}
	
	public String stageId;
	public String stageDesc;


	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}

	

}