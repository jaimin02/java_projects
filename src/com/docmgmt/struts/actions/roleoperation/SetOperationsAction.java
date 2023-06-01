package com.docmgmt.struts.actions.roleoperation;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.server.db.*;
import java.util.*;

public class SetOperationsAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		roleoperation = docMgmtImpl.getOperationParentDetailByParentCode("-999");
		alloperationvector = docMgmtImpl.getAllOperationDetail();
		return SUCCESS;
	}
	
	public Vector roleoperation;
	public Vector alloperationvector;

	public Vector getRoleoperation() {
		return roleoperation;
	}
	public void setRoleoperation(Vector roleoperation) {
		this.roleoperation = roleoperation;
	}
	public Vector getAlloperationvector() {
		return alloperationvector;
	}
	public void setAlloperationvector(Vector alloperationvector) {
		this.alloperationvector = alloperationvector;
	}
	
}
