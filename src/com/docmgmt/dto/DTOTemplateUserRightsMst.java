
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOTemplateUserRightsMst implements Serializable{
	String templateId;
	int UserCode;
	int UserGroupCode;
	int NodeId;
	char CanReadFlag;
	char CanAddFlag;
	char CanEditFlag;
	char CanDeleteFlag;
	String AdvancedRights;
	String Remark;
	int modifyBy;       
	Timestamp modifyOn;  
	char statusIndi;
	
	String userGroupName;
	String userName;
	String nodeDisplayName;
	String workSpaceDesc;
	String readRights;
	String editRights;
	String nodeName;
	String sortOn;
	String sortBy;
	int stageId;
	String stageDesc;
	String templateDesc;
	
	public String getStageDesc() {
		return stageDesc;
	}
	public void setStageDesc(String stageDesc) {
		this.stageDesc = stageDesc;
	}
	public int getStageId() {
		return stageId;
	}
	public void setStageId(int stageId) {
		this.stageId = stageId;
	}
	

	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public int getUserCode() {
		return UserCode;
	}
	public void setUserCode(int UserCode) {
		this.UserCode = UserCode;
	}

	public int getUserGroupCode() {
		return UserGroupCode;
	}
	public void setUserGroupCode(int UserGroupCode) {
		this.UserGroupCode = UserGroupCode;
	}
	
	public int getNodeId() {
		return NodeId;
	}
	public void setNodeId(int NodeId) {
		this.NodeId = NodeId;
	}	
	
	public char getCanReadFlag() {
		return CanReadFlag;
	}
	public void setCanReadFlag(char CanReadFlag) {
		this.CanReadFlag = CanReadFlag;
	}	

	public char getCanAddFlag() {
		return CanAddFlag;
	}
	public void setCanAddFlag(char CanAddFlag) {
		this.CanAddFlag = CanAddFlag;
	}	

	public char getCanEditFlag() {
		return CanEditFlag;
	}
	public void setCanEditFlag(char CanEditFlag) {
		this.CanEditFlag = CanEditFlag;
	}	

	public char getCanDeleteFlag() {
		return CanDeleteFlag;
	}
	public void setCanDeleteFlag(char CanDeleteFlag) {
		this.CanDeleteFlag = CanDeleteFlag;
	}
	
	public String getAdvancedRights() {
		return AdvancedRights;
	}
	public void setAdvancedRights(String AdvancedRights) {
		this.AdvancedRights = AdvancedRights;
	}
	

	public String getRemark() {
		return Remark;
	}
	public void setRemark(String Remark) {
		this.Remark = Remark;
	}

	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public char getStatusIndi() {
		return statusIndi;
	}

	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}

	/**
	 * @return Returns the nodeDisplayName.
	 */
	public String getNodeDisplayName() {
		return nodeDisplayName;
	}
	/**
	 * @param nodeDisplayName The nodeDisplayName to set.
	 */
	public void setNodeDisplayName(String nodeDisplayName) {
		this.nodeDisplayName = nodeDisplayName;
	}
	/**
	 * @return Returns the userGroupName.
	 */
	public String getUserGroupName() {
		return userGroupName;
	}
	/**
	 * @param userGroupName The userGroupName to set.
	 */
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName The userName to set.
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return Returns the workSpaceDesc.
	 */
	public String getWorkSpaceDesc() {
		return workSpaceDesc;
	}
	/**
	 * @param workSpaceDesc The workSpaceDesc to set.
	 */
	public void setWorkSpaceDesc(String workSpaceDesc) {
		this.workSpaceDesc = workSpaceDesc;
	}
	/**
	 * @return Returns the editRights.
	 */
	public String getEditRights() {
		return editRights;
	}
	/**
	 * @param editRights The editRights to set.
	 */
	public void setEditRights(String editRights) {
		this.editRights = editRights;
	}
	/**
	 * @return Returns the readRights.
	 */
	public String getReadRights() {
		return readRights;
	}
	/**
	 * @param readRights The readRights to set.
	 */
	public void setReadRights(String readRights) {
		this.readRights = readRights;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	public String getSortBy() {
		return sortBy;
	}
	
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String getSortOn() {
		return sortOn;
	}
	
	public void setSortOn(String sortOn) {
		this.sortOn = sortOn;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	public String getTemplateDesc() {
		return templateDesc;
	}
	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}
}
