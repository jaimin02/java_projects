package com.docmgmt.struts.actions.master.department;

import com.docmgmt.dto.DTODepartmentMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteDeptAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTODepartmentMst dto =docMgmtImpl.getDepartmentInfo(deptCode);
		usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		dto.setRemark(remark);
		dto.setModifyBy(usercode);
		docMgmtImpl.DepartmentMstOp(dto, 2, true);
	    return SUCCESS;
	}	/**********************************************/
	
	public String statusIndi;
	public String deptCode;
	public String remark;
	public int usercode;

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
   
	
	
	/**********************************************/
	
	
	
}
