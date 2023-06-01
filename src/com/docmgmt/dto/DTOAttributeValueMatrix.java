package com.docmgmt.dto;


import java.sql.Timestamp;


public class DTOAttributeValueMatrix  implements IDTONodeAttr{

	String attrName;
	int attrValueId;
	int attrId;
	String attrValue;
	char projectType;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	String attrType;
	String remark;
	
	String attrMatrixValue;
	String attrForIndiId;
	char userInput;
	String userTypeCode;
	String attrMatrixDisplayValue;
	
	String attrOldValue;
	
	
	
	public String getAttrOldValue() {
		return attrOldValue;
	}
	public void setAttrOldValue(String attrOldValue) {
		this.attrOldValue = attrOldValue;
	}
	public String getAttrMatrixDisplayValue() {
		return attrMatrixDisplayValue;
	}
	public void setAttrMatrixDisplayValue(String attrMatrixDisplayValue) {
		this.attrMatrixDisplayValue = attrMatrixDisplayValue;
	}
	public int getAttrId() {
		return attrId;
	}
	public void setAttrId(int attrId) {
		this.attrId = attrId;
	}
	public String getAttrValue() {
		return attrValue;
	}
	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
	public int getAttrValueId() {
		return attrValueId;
	}
	public void setAttrValueId(int attrValueId) {
		this.attrValueId = attrValueId;
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
	public char getProjectType() {
		return projectType;
	}
	public void setProjectType(char projectType) {
		this.projectType = projectType;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
    public String getAttrType() {
        return attrType;
    }
    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }
    public String getAttrName() {
        return attrName;
    }
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
	public String getAttrMatrixValue() {
		return attrMatrixValue;
	}
	public void setAttrMatrixValue(String attrMatrixValue) {
		this.attrMatrixValue = attrMatrixValue;
	}
	public String getAttrForIndiId() {
		return attrForIndiId;
	}
	public void setAttrForIndiId(String attrForIndiId) {
		this.attrForIndiId = attrForIndiId;
	}
	public char getUserInput() {
		return userInput;
	}
	public void setUserInput(char userInput) {
		this.userInput = userInput;
	}
	public String getUserTypeCode() {
		return userTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		this.userTypeCode = userTypeCode;
	}
 
}
