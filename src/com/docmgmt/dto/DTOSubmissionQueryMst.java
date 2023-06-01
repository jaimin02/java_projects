package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DTOSubmissionQueryMst implements Serializable {

	private static final long serialVersionUID = 1L;
	long queryId;
	String queryTitle;
	Timestamp startDate;
	Timestamp endDate;
	String refDoc;
	String queryDesc;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;
	String ISTDateTime;
	String ESTDateTime;
	String endISTDateTime;
	String endESTDateTime;
	
	
	ArrayList<DTOSubmissionQueryDtl> submissionQueryDtls;
	
	public String getEndISTDateTime() {
		return endISTDateTime;
	}
	public void setEndISTDateTime(String endISTDateTime) {
		this.endISTDateTime = endISTDateTime;
	}
	
	public String getEndESTDateTime() {
		return endESTDateTime;
	}
	public void setEndESTDateTime(String endESTDateTime) {
		this.endESTDateTime = endESTDateTime;
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
	
	public long getQueryId() {
		return queryId;
	}
	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}
	public String getQueryTitle() {
		return queryTitle;
	}
	public void setQueryTitle(String queryTitle) {
		this.queryTitle = queryTitle;
	}
	
	public String getRefDoc() {
		return refDoc;
	}
	public void setRefDoc(String refDoc) {
		this.refDoc = refDoc;
	}
	public String getQueryDesc() {
		return queryDesc;
	}
	public void setQueryDesc(String queryDesc) {
		this.queryDesc = queryDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	
	public ArrayList<DTOSubmissionQueryDtl> getSubmissionQueryDtls() {
		return submissionQueryDtls;
	}
	public void setSubmissionQueryDtls(
			ArrayList<DTOSubmissionQueryDtl> submissionQueryDtls) {
		this.submissionQueryDtls = submissionQueryDtls;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}
