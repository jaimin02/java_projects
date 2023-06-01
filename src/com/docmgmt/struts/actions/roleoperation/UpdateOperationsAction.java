package com.docmgmt.struts.actions.roleoperation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOOperationMst;
import com.docmgmt.server.db.*;


public class UpdateOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTOOperationMst dto = new DTOOperationMst();
		dto.setOperationCode(operationCode);
		dto.setOperationName(operationName);
		dto.setOperationPath(operationPath);
		dto.setParentOperationCode(parentOperationCode);
		dto.setModifyBy(userCode);
		dto.setStatusIndi('E');
		docMgmtImpl.InsertIntoOperationMst(dto,2);
		return SUCCESS;
	}
		
	public String operationName;
	public String operationPath;
	public String operationCode;
	public String parentOperationCode;

	public String getParentOperationCode() {
		return parentOperationCode;
	}
	public void setParentOperationCode(String parentOperationCode) {
		this.parentOperationCode = parentOperationCode;
	}
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
	
}
