/*
 * Created on Nov 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @auor Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTOSubmissionInfoHTML implements Serializable{

	
	long submissionInfoHtmlID;
	String workSpaceId;
	int tranNo;
	String description;
	String publishPath;
	int createdBy;
	int modifyBy;
	Timestamp modifyon;
	char statusIndi;
	String workspacedesc;
	String username;
	String ISTDateTime;
	String ESTDateTime;
	double publishFolderSize;
	
		
	
	public double getPublishFolderSize() {
		return publishFolderSize;
	}
	public void setPublishFolderSize(double publishFolderSize) {
		this.publishFolderSize = publishFolderSize;
	}
	public String getISTDateTime() {
		return ISTDateTime;
	}
	public void setISTDateTime(String iSTDateTime) {
		ISTDateTime = iSTDateTime;
	}
	public String getESTDateTime() {
		return ESTDateTime;
	}
	public void setESTDateTime(String eSTDateTime) {
		ESTDateTime = eSTDateTime;
	}
	
	public long getSubmissionInfoHtmlID() {
		return submissionInfoHtmlID;
	}
	public void setSubmissionInfoHtmlID(long submissionInfoHtmlID) {
		this.submissionInfoHtmlID = submissionInfoHtmlID;
	}
	public String getWorkSpaceId() {
		return workSpaceId;
	}
	public void setWorkSpaceId(String workSpaceId) {
		this.workSpaceId = workSpaceId;
	}
	public int getTranNo() {
		return tranNo;
	}
	public void setTranNo(int tranNo) {
		this.tranNo = tranNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPublishPath() {
		return publishPath;
	}
	public void setPublishPath(String publishPath) {
		this.publishPath = publishPath;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getModifyon() {
		return modifyon;
	}
	public void setModifyon(Timestamp modifyon) {
		this.modifyon = modifyon;
	}
	public char getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(char statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getWorkspacedesc() {
		return workspacedesc;
	}
	public void setWorkspacedesc(String workspacedesc) {
		this.workspacedesc = workspacedesc;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	}
