package com.docmgmt.struts.actions.roleoperation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOOperationMst;
import com.docmgmt.server.db.*;


public class DeleteOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOOperationMst dto = new DTOOperationMst();
		dto.setOperationCode(operationCode);
		dto.setModifyBy(userCode);
		if(statusIndi.equals("N")|| statusIndi.equals("E")){
			dto.setStatusIndi('I');
		}
		else if(statusIndi.equals("I"))
		{
			dto.setStatusIndi('E');
		}
		docMgmtImpl.InsertIntoOperationMst(dto,3);
		return SUCCESS;
	}
		
	
	public String operationCode;
	public String statusIndi;

	
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
}
