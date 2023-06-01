package com.docmgmt.struts.actions.master.roleMst;


import com.docmgmt.dto.DTOClientMst;
import com.docmgmt.dto.DTORoleMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveRoleAction extends ActionSupport {//implements Preparable{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	
	@Override
	public String execute()
	{
		DTORoleMst dto = new DTORoleMst();
		dto.setRemark("New");
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setModifyBy(userId);
	    dto.setRoleName(roleName.trim());
	    
	    boolean clientExist = docMgmtImpl.roleNameExist(roleName.trim());
	    if(!clientExist)
	        docMgmtImpl.RoleMstOp(dto, 1,false);
		else{
			addActionError("Role Already Exists!");
			return INPUT;
		}
	    return SUCCESS;
	}
	
	public String UpdateClient()
	{
		DTOClientMst dto = new DTOClientMst();
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		dto.setRemark(remark);
		dto.setClientCode(roleCode);
		dto.setClientName(roleName.trim());
		dto.setModifyBy(userId);
	    boolean clientExist = docMgmtImpl.clientNameExist(roleName.trim());
	    if(!clientExist)
	    {
	    	docMgmtImpl.ClientMstOp(dto, 2,false);
	    }
	   	return SUCCESS;
	}
	
	public String roleName;
	public String roleCode;
	public String statusIndi;
	public String remark;

	public String getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(String statusIndi) {
		this.statusIndi = statusIndi;
	}

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
