package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOSequenceDescriptionMst_CA {
	String seqDescCode;
	String seqDescSubTypeCode;
	String sequenceDescription;
	String labelName;
	String fieldType;
	int modifyBy;       
	Timestamp modifyOn;            
	char statusIndi;
	public String getSeqDescCode() {
		return seqDescCode;
	}
	public void setSeqDescCode(String seqDescCode) {
		this.seqDescCode = seqDescCode;
	}
	public String getSeqDescSubTypeCode() {
		return seqDescSubTypeCode;
	}
	public void setSeqDescSubTypeCode(String seqDescSubTypeCode) {
		this.seqDescSubTypeCode = seqDescSubTypeCode;
	}
	public String getSequenceDescription() {
		return sequenceDescription;
	}
	public void setSequenceDescription(String sequenceDescription) {
		this.sequenceDescription = sequenceDescription;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
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
