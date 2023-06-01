package com.docmgmt.struts.actions.roleoperation;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class GetParentOperationDetailAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		getParentOpDetail=docMgmtImpl.getOperationParentDetailByParentCode(operationCode);
		return SUCCESS;
	}
	
	public String operationCode;
	public Vector getParentOpDetail;


	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public Vector getGetParentOpDetail() {
		return getParentOpDetail;
	}
	public void setGetParentOpDetail(Vector getParentOpDetail) {
		this.getParentOpDetail = getParentOpDetail;
	}


	
}
