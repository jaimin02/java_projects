package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOAttrForIndiMst {
	
	String attrForIndiId;
	String attrForIndiName;
	String remark;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
	public String getAttrForIndiName() {
		return attrForIndiName;
	}
	public void setAttrForIndiName(String attrForIndiName) {
		this.attrForIndiName = attrForIndiName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Timestamp getModifyOn() {
		return modifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		this.modifyOn = modifyOn;
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

}
