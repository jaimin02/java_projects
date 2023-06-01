package com.docmgmt.struts.actions.master.stage;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class AddStageAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		stageDetail = docMgmtImpl.getStageDetail(); 
		return SUCCESS;
	}	

	private Vector stageDetail;

	public Vector getStageDetail() {
		return stageDetail;
	}

	
    
}
