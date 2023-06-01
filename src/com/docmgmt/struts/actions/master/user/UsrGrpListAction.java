package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class UsrGrpListAction  extends ActionSupport
{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	public String userTypeCode;
	
	public Vector usergrplist=new Vector();
	
	
	
	@Override
	public String execute()
	{
		System.out.println(userTypeCode +"***************userTypeCode");
		usergrplist=docMgmtImpl.getAllUserGroupByUserType(userTypeCode);
		return SUCCESS;
	}

	
	public String getUserTypeCode() {
		return userTypeCode;
	}

	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}

	public Vector getUsergrplist() {
		return usergrplist;
	}

	public void setUsergrplist(Vector usergrplist) {
		this.usergrplist = usergrplist;
	}
}
