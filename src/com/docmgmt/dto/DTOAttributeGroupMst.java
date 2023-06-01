/*
 * Created on Aug 11, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOAttributeGroupMst implements Serializable{

	String attrGroupId;
	String projectName;
	String attrGroupName;
	String projectCode;
	String porjectType;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	
	public String getAttrGroupId() {
		return attrGroupId;
	}
	public void setAttrGroupId(String attrGroupId) {
		this.attrGroupId = attrGroupId;
	}
	public String getAttrGroupName() {
		return attrGroupName;
	}
	public void setAttrGroupName(String attrGroupName) {
		this.attrGroupName = attrGroupName;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
	}
	
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getPorjectType() {
		return porjectType;
	}
	public void setPorjectType(String porjectType) {
		this.porjectType = porjectType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
