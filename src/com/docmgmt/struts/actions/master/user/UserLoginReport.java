package com.docmgmt.struts.actions.master.user;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class UserLoginReport extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public Vector allUserType;
		@Override
	public String execute()
	{
			allUserType = docMgmtImpl.getAllUserType();
			return SUCCESS;
	}
		
}
