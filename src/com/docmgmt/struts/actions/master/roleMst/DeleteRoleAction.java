package com.docmgmt.struts.actions.master.roleMst;

import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteRoleAction extends ActionSupport   {
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		usercode = Integer.parseInt(ActionContext.getContext()
				.getSession().get("userid").toString());
		DTORoleMst dto =docMgmtImpl.getRoleInfo(roleCode);
		dto.setStatusIndi(statusIndi.charAt(0));
		dto.setRemark(remark);
		dto.setModifyBy(usercode);
		docMgmtImpl.RoleMstOp(dto, 2,true);
	    return SUCCESS;
	}	
	
	public String statusIndi;
	public String roleCode;
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
	public String getClientCode() {
		return roleCode;
	}
	public void setClientCode(String clientCode) {
		this.roleCode = clientCode;
	}
	
   
	
	
	/**********************************************/
	
	
	
}

