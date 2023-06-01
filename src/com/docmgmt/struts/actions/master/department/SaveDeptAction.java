package com.docmgmt.struts.actions.master.department;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class SaveDeptAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	
	@Override
	public String execute()
	{
		DTODepartmentMst dto = new DTODepartmentMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark("New");
		dto.setModifyBy(userId);
	    dto.setDeptName(deptName.trim());
	    boolean deptExist = docMgmtImpl.deptNameExist(deptName.trim());
	  
	    if(!deptExist)
	   		docMgmtImpl.DepartmentMstOp(dto,1,false);
		else{
	    	addActionError("Department Name Already Exists!");
	    	return INPUT;
		}
		
		return SUCCESS;
	}
	public String UpdateDept()
	{
		DTODepartmentMst dto=docMgmtImpl.getDepartmentInfo(deptCode);
		dto.setDeptName(deptName);
		dto.setRemark(remark);
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
		boolean deptExist = docMgmtImpl.deptNameExist(deptName.trim());
		if(!deptExist)
		{
			docMgmtImpl.DepartmentMstOp(dto,2,false);
		}
		return SUCCESS;
	}
	
	public String deptName;
	public String newDeptName;
	public String remark;
	public String deptCode;
	public String statusIndi;

	public String getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}

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

	public String getNewDeptName() {
		return newDeptName;
	}

	public void setNewDeptName(String newDeptName) {
		this.newDeptName = newDeptName;
	}
	
	
}
