
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionTypeMst implements Serializable{

    String submissionTypeId;
	String submissionType;
    String countryRegionId;
    Timestamp modifyOn;
    int modifyBy;
    char statusIndi;
    char submissionTypeIndi;
    String submissionTypeCode;
    String regionalDTDVersion;
    String submissionTypeValue;
    
	public String getSubmissionTypeCode() {
		return submissionTypeCode;
	}
	public void setSubmissionTypeCode(String submissionTypeCode) {
		this.submissionTypeCode = submissionTypeCode;
	}
	public char getSubmissionTypeIndi() {
		return submissionTypeIndi;
	}
	public void setSubmissionTypeIndi(char submissionTypeIndi) {
		this.submissionTypeIndi = submissionTypeIndi;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getSubmissionTypeId() {
		return submissionTypeId;
	}
	public void setSubmissionTypeId(String submissionTypeId) {
		this.submissionTypeId = submissionTypeId;
	}
	public String getSubmissionType() {
		return submissionType;
	}
	public void setSubmissionType(String submissionType) {
		this.submissionType = submissionType;
	}
	public String getCountryRegionId() {
		return countryRegionId;
	}
	public void setCountryRegionId(String countryRegionId) {
		this.countryRegionId = countryRegionId;
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
	public String getRegionalDTDVersion() {
		return regionalDTDVersion;
	}
	public void setRegionalDTDVersion(String regionalDTDVersion) {
		this.regionalDTDVersion = regionalDTDVersion;
	}
	public String getSubmissionTypeValue() {
		return submissionTypeValue;
	}
	public void setSubmissionTypeValue(String submissionTypeValue) {
		this.submissionTypeValue = submissionTypeValue;
	}
	
    
	}
