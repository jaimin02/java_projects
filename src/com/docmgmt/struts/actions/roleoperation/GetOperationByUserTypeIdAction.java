package com.docmgmt.struts.actions.roleoperation;



import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.Vector;

public class GetOperationByUserTypeIdAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		getOpeartons=docMgmtImpl.getOperationByUserTypeId(userType);
		return SUCCESS;
	}
	
	public String userType;
	public Vector getOpeartons;


	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Vector getGetOpeartons() {
		return getOpeartons;
	}
	public void setGetOpeartons(Vector getOpeartons) {
		this.getOpeartons = getOpeartons;
	}
	
}
