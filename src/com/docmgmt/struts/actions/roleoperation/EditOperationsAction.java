package com.docmgmt.struts.actions.roleoperation;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;


public class EditOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		roleoperation = docMgmtImpl.getOperationParentDetailByParentCode("-999");
		return SUCCESS;
	}
		
	public String operationName;
	public String operationPath;
	public String operationCode;
	public String parentOperationCode;
	public Vector roleoperation;

	public Vector getRoleoperation() {
		return roleoperation;
	}
	public void setRoleoperation(Vector roleoperation) {
		this.roleoperation = roleoperation;
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
	public String getParentOperationCode() {
		return parentOperationCode;
	}
	public void setParentOperationCode(String parentOperationCode) {
		this.parentOperationCode = parentOperationCode;
	}
	
}
