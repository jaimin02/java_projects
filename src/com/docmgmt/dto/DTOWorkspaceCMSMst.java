
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOWorkspaceCMSMst implements Serializable{

	int workspaceCMSId;
	String workspaceId;
	String countryCode;
	String agencyCode;
	int waveNo;
	Timestamp modifyOn;
	int modifyBy;
	char statusIndi; 
	
	String countryName;
	String countryRegion;
	String countryCodeName;
	
	String agencyName;
	String agencyFullName;
	String cmsTrackNum;
	String inventedName;
	
	
	ArrayList<DTOSubmissionInfoEUSubDtl> CMSSubmissionDtl;
	
	public int getWorkspaceCMSId() {
		return workspaceCMSId;
	}
	public void setWorkspaceCMSId(int workspaceCMSId) {
		this.workspaceCMSId = workspaceCMSId;
	}
	public String getWorkspaceId() {
		return workspaceId;
	}
	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public int getWaveNo() {
		return waveNo;
	}
	public void setWaveNo(int waveNo) {
		this.waveNo = waveNo;
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
	
	public ArrayList<DTOSubmissionInfoEUSubDtl> getCMSSubmissionDtl() {
		return CMSSubmissionDtl;
	}
	public void setCMSSubmissionDtl(
			ArrayList<DTOSubmissionInfoEUSubDtl> submissionDtl) {
		CMSSubmissionDtl = submissionDtl;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryRegion() {
		return countryRegion;
	}
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}
	public String getCountryCodeName() {
		return countryCodeName;
	}
	public void setCountryCodeName(String countryCodeName) {
		this.countryCodeName = countryCodeName;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getAgencyFullName() {
		return agencyFullName;
	}
	public void setAgencyFullName(String agencyFullName) {
		this.agencyFullName = agencyFullName;
	}
	public String getCmsTrackNum() {
		return cmsTrackNum;
	}
	public void setCmsTrackNum(String cmsTrackNum) {
		this.cmsTrackNum = cmsTrackNum;
	}
	public String getInventedName() {
		return inventedName;
	}
	public void setInventedName(String inventedName) {
		this.inventedName = inventedName;
	}
	
}
