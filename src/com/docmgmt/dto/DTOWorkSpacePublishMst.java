
/*
 * Created on Dec 04, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOWorkSpacePublishMst implements Serializable{
	
	int publishId;
	String workspaceId;                     
	String workspaceLabelId;	
	String remark;
	char revisedIndi;
	char statusIndi;
	int modifyBy;
	Timestamp modifyOn;
	String loginName;
	
	
	/**
	 * @return Returns the loginName.
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName The loginName to set.
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return Returns the modifyBy.
	 */
	public int getModifyBy() {
		return modifyBy;
	}
	/**
	 * @param modifyBy The modifyBy to set.
	 */
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	/**
	 * @return Returns the modifyOn.
	 */
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	/**
	 * @param modifyOn The modifyOn to set.
	 */
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	/**
	 * @return Returns the publishId.
	 */
	public int getPublishId() {
		return publishId;
	}
	/**
	 * @param publishId The publishId to set.
	 */
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	/**
	 * @return Returns the remark.
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return Returns the revisedIndi.
	 */
	public char getRevisedIndi() {
		return revisedIndi;
	}
	/**
	 * @param revisedIndi The revisedIndi to set.
	 */
	public void setRevisedIndi(char revisedIndi) {
		this.revisedIndi = revisedIndi;
	}
	/**
	 * @return Returns the statusIndi.
	 */
	public char getStatusIndi() {
		return statusIndi;
	}
	/**
	 * @param statusIndi The statusIndi to set.
	 */
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	/**
	 * @return Returns the workspaceId.
	 */
	public String getWorkspaceId() {
		return workspaceId;
	}
	/**
	 * @param workspaceId The workspaceId to set.
	 */
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	/**
	 * @return Returns the workspaceLabelId.
	 */
	public String getWorkspaceLabelId() {
		return workspaceLabelId;
	}
	/**
	 * @param workspaceLabelId The workspaceLabelId to set.
	 */
	public void setWorkspaceLabelId(String workspaceLabelId) {
		this.workspaceLabelId = workspaceLabelId;
	}
}
