package com.docmgmt.struts.actions.roleoperation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOOperationMst;
import com.docmgmt.server.db.*;


public class SaveOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOOperationMst dto = new DTOOperationMst();
		dto.setOperationName(operationName);
		dto.setOperationPath(operationPath);
		dto.setParentOperationCode(operationCode);
		dto.setModifyBy(userCode);
		dto.setStatusIndi(statusIndi.charAt(0));
		docMgmtImpl.InsertIntoOperationMst(dto,1);
		return SUCCESS;
	}
		
	public String operationName;
	public String operationPath;
	public String operationCode;
	public String statusIndi;

	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getOperationPath() {
		return operationPath;
	}
	public void setOperationPath(String operationPath) {
		this.operationPath = operationPath;
	}
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
