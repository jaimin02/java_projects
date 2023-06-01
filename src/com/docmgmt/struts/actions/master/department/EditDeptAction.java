package com.docmgmt.struts.actions.master.department;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;

public class EditDeptAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	@Override
	public String execute()
	{
		
	    return SUCCESS;	
	}
	
	public String deptDetailHistory(){	
		
		deptDetailHistory = docMgmtImpl.getDeptDetailHistory(deptCode);
		return SUCCESS;	
	}
	
	public Vector deptDetailHistory;
	
	public String deptCode;
	public String deptName;
	
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	
}