package com.docmgmt.struts.actions.master.stage;



import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOStageMst;
import com.docmgmt.server.db.*;

public class SaveStageAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	@Override
	public String execute()
	{
		DTOStageMst dto = new DTOStageMst();
		dto.setInclusive(inclusive);
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
		dto.setStageDesc(stageDesc);
	    docMgmtImpl.stageMstOp(dto, 1,false);
		return SUCCESS;
		
	}
	
	public String UpdateStage()
	{
		
		DTOStageMst dto = new DTOStageMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setStageId(stageId);
		dto.setStageDesc(stageDesc);
		dto.setInclusive(inclusive);
		dto.setModifyBy(userId);
		docMgmtImpl.stageMstOp(dto, 2,false);
	
		return SUCCESS;
	}
	
	public String stageDesc;
	public int stageId;
	public String statusIndi;
	public char inclusive;


	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	
	public int getStageId() {
		return stageId;
	}

	public void setStageId(int stageId) {
		this.stageId = stageId;
	}

	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public char getInclusive() {
		return inclusive;
	}
	public void setInclusive(char inclusive) {
		this.inclusive = inclusive;
	}

	
	
}