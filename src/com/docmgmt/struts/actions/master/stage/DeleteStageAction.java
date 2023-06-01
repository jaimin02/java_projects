package com.docmgmt.struts.actions.master.stage;

import com.docmgmt.dto.DTOStageMst;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;

public class DeleteStageAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{

		DTOStageMst dto = docMgmtImpl.getStageInfo(stageId);
		dto.setStatusIndi(statusIndi);
		docMgmtImpl.stageMstOp(dto, 2,true);
	    return SUCCESS;
	}	
	
	public char statusIndi;
	public int stageId;


	
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}


	
	
   
	
	
	/**********************************************/
	
	
	
}
