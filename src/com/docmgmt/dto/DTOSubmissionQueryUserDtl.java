package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOSubmissionQueryUserDtl implements Serializable{

	
	
	
	long queryId;
	int userid;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	char statusIndi;

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
	public long getQueryId() {
		return queryId;
	}
	public void setQueryId(long queryId) {
		this.queryId = queryId;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
}	
