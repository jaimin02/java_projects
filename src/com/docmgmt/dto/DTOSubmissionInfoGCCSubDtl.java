package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionInfoGCCSubDtl implements Serializable{

	int submissionInfoGCCSubDtlId;
	int workspaceCMSId;	
	String submissionInfoGCCDtlId;
	Timestamp dateOfSubmission;
	String submissionDescription;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi;
	
	String workspaceId;
	int waveNo;
	String countryName;
	String countryCodeName;
	String agencyName;
	String agencyCode;
	String countryCode;
	String publishCMSTrackingNo;
	String CMSTrackingNo;
	
	public int getSubmissionInfoGCCSubDtlId() {
		return submissionInfoGCCSubDtlId;
	}
	public void setSubmissionInfoGCCSubDtlId(int submissionInfoGCCSubDtlId) {
		this.submissionInfoGCCSubDtlId = submissionInfoGCCSubDtlId;
	}
	public int getWorkspaceCMSId() {
		return workspaceCMSId;
	}
	public void setWorkspaceCMSId(int workspaceCMSId) {
		this.workspaceCMSId = workspaceCMSId;
	}
	public String getSubmissionInfoEU14DtlId() {
		return submissionInfoGCCDtlId;
	}
	public void setSubmissionInfoGCCDtlId(String submissionInfoGCCDtlId) {
		this.submissionInfoGCCDtlId = submissionInfoGCCDtlId;
	}
	public Timestamp getDateOfSubmission() {
		return dateOfSubmission;
	}
	public void setDateOfSubmission(Timestamp dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
	}
	public String getSubmissionDescription() {
		return submissionDescription;
	}
	public void setSubmissionDescription(String submissionDescription) {
		this.submissionDescription = submissionDescription;
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
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public int getWaveNo() {
		return waveNo;
	}
	public void setWaveNo(int waveNo) {
		this.waveNo = waveNo;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryCodeName() {
		return countryCodeName;
	}
	public void setCountryCodeName(String countryCodeName) {
		this.countryCodeName = countryCodeName;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPublishCMSTrackingNo() {
		return publishCMSTrackingNo;
	}
	public void setPublishCMSTrackingNo(String publishCMSTrackingNo) {
		this.publishCMSTrackingNo = publishCMSTrackingNo;
	}
	public String getCMSTrackingNo() {
		return CMSTrackingNo;
	}
	public void setCMSTrackingNo(String cMSTrackingNo) {
		CMSTrackingNo = cMSTrackingNo;
	}
}
