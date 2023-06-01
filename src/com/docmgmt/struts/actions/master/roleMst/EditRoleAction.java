package com.docmgmt.struts.actions.master.roleMst;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditRoleAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		
	    return SUCCESS;
		
		
	}
	
	
	public String roleDetailHistory(){
		
		roleDetailHistory = docMgmtImpl.getRoleDetailHistory(roleCode);
		return SUCCESS;
		
	}
	
	
	public Vector roleDetailHistory;
	
	public String roleName;
	public String roleCode;

	public String getClientName() {
		return roleName;
	}

	public void setClientName(String clientName) {
		this.roleName = clientName;
	}
	
	public String getClientCode() {
		return roleCode;
	}

	public void setClientCode(String clientCode) {
		this.roleCode = clientCode;
	}

}
