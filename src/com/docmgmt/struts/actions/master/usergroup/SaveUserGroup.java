package com.docmgmt.struts.actions.master.usergroup;

import java.util.Vector;

import com.docmgmt.dto.DTOUserGroupMst;
import com.docmgmt.server.db.DocMgmtImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SaveUserGroup extends ActionSupport
{
	private static final long serialVersionUID = 1L;

	private DocMgmtImpl docMgmtImpl=new DocMgmtImpl();

	public int userGroupCode;
	
	public String userGroupName;
	public String locationCode;
	public String deptCode;
	public String clientCode;
	public String userTypeCode;
	public String projectCode;
	public String remark;
	public String errorMsg;
	public String htmlContent;
	
	public int getUserGroupCode() {
		return userGroupCode;
	}
	public void setUserGroupCode(int userGroupCode) {
		this.userGroupCode = userGroupCode;
	}
	

	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
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
	public String getClientCode() {
		return clientCode;
	}
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}



	@Override
	public String execute()
	{
		errorMsg = "";
		/*
		when a new user group is added,
		below validation will check, whether same group name already exists or not.
		*/
		
		Vector<DTOUserGroupMst> allUserGroups = docMgmtImpl.getUserGroupDtl();
		for (int indUsrGroup = 0;indUsrGroup < allUserGroups.size();indUsrGroup++)
		{
			DTOUserGroupMst userGroup = allUserGroups.get(indUsrGroup);
			if (userGroup.getUserGroupName().equals(userGroupName))
			{
				addActionError("Group Name already exists!!!");
				//System.out.println("Group Name already exists!!!");
				errorMsg = "Group Name already exists!!!";
				return SUCCESS;
			}
		}
		
		DTOUserGroupMst obj=new DTOUserGroupMst();
		
		obj.setClientCode(clientCode);
		obj.setDeptCode(deptCode);
		obj.setLocationCode(locationCode);
		obj.setProjectCode(projectCode);
		obj.setUserTypeCode(userTypeCode);
		obj.setUserGroupName(userGroupName);
		obj.setRemark(remark);
		obj.setModifyBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		
		docMgmtImpl.UserGroupMstOp(obj,1,false);
		return SUCCESS;
	}

	
	public String UpdateUserGroup()
	{
		
		DTOUserGroupMst obj=new DTOUserGroupMst();
		obj.setUserGroupCode(userGroupCode);
		obj.setClientCode(clientCode);
		obj.setDeptCode(deptCode);
		obj.setLocationCode(locationCode);
		obj.setProjectCode(projectCode);
		obj.setUserTypeCode(userTypeCode);
		obj.setUserGroupName(userGroupName);
		obj.setRemark(remark);
		obj.setModifyBy(Integer.parseInt(ActionContext.getContext().getSession().get("userid").toString()));
		
		docMgmtImpl.UserGroupMstOp(obj,2,false);
		htmlContent = "User Details Updated Successfully..";
		
		return SUCCESS;
	}

}
