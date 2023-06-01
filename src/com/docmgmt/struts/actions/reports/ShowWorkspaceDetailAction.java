package com.docmgmt.struts.actions.reports;

import java.util.Vector;

import com.opensymphony.xwork2.ActionSupport;

import com.docmgmt.dto.DTOUserMst;
import com.docmgmt.dto.DTOWorkSpaceMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;

public class ShowWorkspaceDetailAction extends ActionSupport{
	
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();
	
	@Override
	public String execute()
	{
		int userId = Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString());
		int userGroupCode = Integer.parseInt(ActionContext.getContext().getSession().get("usergroupcode").toString());
		String userType = ActionContext.getContext().getSession().get("usertypename").toString();
		DTOUserMst userMst = new DTOUserMst();
		userMst.setUserGroupCode(userGroupCode);
		userMst.setUserCode(userId);
		userMst.setUserType(userType);
		DTOWorkSpaceMst dto=new DTOWorkSpaceMst();
		dto.setLocationCode(locationCode);
		dto.setDeptCode(deptCode);
		//wsDtl = docMgmtImpl.getWorkspaceDetailByLocDept(locationCode,deptCode);
		wsDtl = docMgmtImpl.searchWorkspace(dto,userMst);
		return SUCCESS;
	}
	
	public Vector wsDtl;
	public String locationCode;
	public String  deptCode;

	public Vector getWsDtl() {
		return wsDtl;
	}
	public void setWsDtl(Vector wsDtl) {
		this.wsDtl = wsDtl;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
	
	


