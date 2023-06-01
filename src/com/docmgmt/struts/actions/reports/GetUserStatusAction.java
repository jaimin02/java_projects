package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class GetUserStatusAction extends ActionSupport{
	public String userName;
	int userCode;
	int userGroupCode;
	public Vector<DTOUserMst> userstatusdata = new Vector<DTOUserMst>();
	
	DocMgmtImpl docmgmt = new DocMgmtImpl();
	public String execute()
	{
		userCode = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString()); 
		
		
		userstatusdata = docmgmt.getUserStatus(userCode, userGroupCode);
		
		
		return SUCCESS;
	}
	
}
