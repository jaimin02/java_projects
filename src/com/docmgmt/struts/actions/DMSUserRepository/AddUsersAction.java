package com.docmgmt.struts.actions.DMSUserRepository;

import java.util.Vector;

import com.docmgmt.dto.DTOUserTypeMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class AddUsersAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	public Vector<DTOUserTypeMst> userTypes;
	
	@Override
	public String execute() throws Exception
	{
		userTypes = docMgmtImpl.getAllUserType();
		return SUCCESS;
	}

}
