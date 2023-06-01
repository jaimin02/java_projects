package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOSequenceTypeMst_Thai {
	String sequenceTypeCode;
	String sequenceTypeDescription;
	int modifyBy;       
	Timestamp modifyOn;            
	char statusIndi;
	
	public String getSequenceTypeCode() {
		return sequenceTypeCode;
	}
	public void setSequenceTypeCode(String sequenceTypeCode) {
		this.sequenceTypeCode = sequenceTypeCode;
	}
	public String getSequenceTypeDescription() {
		return sequenceTypeDescription;
	}
	public void setSequenceTypeDescription(String sequenceTypeDescription) {
		this.sequenceTypeDescription = sequenceTypeDescription;
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
