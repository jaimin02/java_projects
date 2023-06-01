package com.docmgmt.struts.actions.roleoperation;



import com.docmgmt.dto.DTORoleOperationMatrix;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;



public class SaveAssignRoleOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;
	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		int userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		DTORoleOperationMatrix dto = new DTORoleOperationMatrix();
		dto.setCode(operationCode);
		dto.setUserTypeCode(userType);
		dto.setActiveFlag(statusIndi.charAt(0));
		dto.setModifyBy(userCode);
		//docMgmtImpl.insertIntoRoleOperationMatrix(dto);
		dto = null;
		for(int i=0; i< code.length; i++)
		{
			dto = new DTORoleOperationMatrix();
			dto.setCode(code[i]);
			dto.setUserTypeCode(userType);
			dto.setActiveFlag(statusIndi.charAt(0));
			dto.setModifyBy(userCode);
			//docMgmtImpl.insertIntoRoleOperationMatrix(dto);
		}
		return SUCCESS;
	}
	
	public String[] code;
	public String operationCode;
	public String userType;
	public String statusIndi;


	public String[] getCode() {
		return code;
	}
	public void setCode(String[] code) {
		this.code = code;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
}
