package com.docmgmt.struts.actions.master.usergroup;

import java.util.Vector;

import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionSupport;
import com.docmgmt.dto.DTOUserGroupMst;

public class EditUserGroup extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	
	Vector userGroupDtl=new Vector();
	Vector clientDtl=new Vector();
	Vector locationDtl=new Vector();
	Vector getAllUserType=new Vector();
	Vector deptDtl=new Vector();
	Vector projectTypeDtl=new Vector();
	DTOUserGroupMst usergroupdata=new DTOUserGroupMst();
	
	public String userGroupCode;

	
	@Override
	public String execute()
	{
		usergroupdata=docMgmtImpl.getUserGroupDtlByGroupId(userGroupCode);
	
		
		 userGroupDtl = docMgmtImpl.getUserGroupDtl();
		 clientDtl = docMgmtImpl.getClientDetail();
		 locationDtl = docMgmtImpl.getLocationDtl();
		 deptDtl = docMgmtImpl.getDepartmentDetail();
		 getAllUserType   = docMgmtImpl.getAllUserType();
		 projectTypeDtl = docMgmtImpl.getProjectType();
		
		return SUCCESS;
	}
	
	
	
	public DTOUserGroupMst getUsergroupdata() {
		return usergroupdata;
	}

	public void setUsergroupdata(DTOUserGroupMst usergroupdata) {
		this.usergroupdata = usergroupdata;
	}
	
	public String getUserGroupCode() {
		return userGroupCode;
	}

	public void setUserGroupCode(String userGroupCode) {
		this.userGroupCode = userGroupCode;
	}

	
	public Vector getUserGroupDtl() {
		return userGroupDtl;
	}

	public void setUserGroupDtl(Vector userGroupDtl) {
		this.userGroupDtl = userGroupDtl;
	}

	public Vector getClientDtl() {
		return clientDtl;
	}

	public void setClientDtl(Vector clientDtl) {
		this.clientDtl = clientDtl;
	}

	public Vector getLocationDtl() {
		return locationDtl;
	}

	public void setLocationDtl(Vector locationDtl) {
		this.locationDtl = locationDtl;
	}

	public Vector getGetAllUserType() {
		return getAllUserType;
	}

	public void setGetAllUserType(Vector getAllUserType) {
		this.getAllUserType = getAllUserType;
	}

	public Vector getDeptDtl() {
		return deptDtl;
	}

	public void setDeptDtl(Vector deptDtl) {
		this.deptDtl = deptDtl;
	}

	public Vector getProjectTypeDtl() {
		return projectTypeDtl;
	}

	public void setProjectTypeDtl(Vector projectTypeDtl) {
		this.projectTypeDtl = projectTypeDtl;
	}
	
}
