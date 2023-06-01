
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOSubmissionInfoEU14SubDtl implements Serializable{

	int submissionInfoEU14SubDtlId;
	int workspaceCMSId;	
	String submissionInfoEU14DtlId;
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
	
	public int getSubmissionInfoEU14SubDtlId() {
		return submissionInfoEU14SubDtlId;
	}
	public void setSubmissionInfoEU14SubDtlId(int submissionInfoEU14SubDtlId) {
		this.submissionInfoEU14SubDtlId = submissionInfoEU14SubDtlId;
	}
	public int getWorkspaceCMSId() {
		return workspaceCMSId;
	}
	public void setWorkspaceCMSId(int workspaceCMSId) {
		this.workspaceCMSId = workspaceCMSId;
	}
	public String getSubmissionInfoEU14DtlId() {
		return submissionInfoEU14DtlId;
	}
	public void setSubmissionInfoEU14DtlId(String submissionInfoEU14DtlId) {
		this.submissionInfoEU14DtlId = submissionInfoEU14DtlId;
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
