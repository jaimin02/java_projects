package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOPasswordPolicyMst {
	
	int policyNo;
	String policyDesc;
	String value;
	char activeFlag;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	
	public int getPolicyNo() {
		return policyNo;
	}
	public void setPolicyNo(int policyNo) {
		this.policyNo = policyNo;
	}
	public String getPolicyDesc() {
		return policyDesc;
	}
	public void setPolicyDesc(String policyDesc) {
		this.policyDesc = policyDesc;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public char getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(char activeFlag) {
		this.activeFlag = activeFlag;
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
	
}
