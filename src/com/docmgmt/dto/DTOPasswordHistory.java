package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOPasswordHistory {
	
	long userID;
	int srNo;
	String password;
	int modifyBy;
	Timestamp changedDate;
	Timestamp modifyOn;
	char statusIndi;
	int DayDiff;
	
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Timestamp getChangedDate() {
		return changedDate;
	}
	public void setChangedDate(Timestamp changedDate) {
		this.changedDate = changedDate;
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
	public int getDayDiff() {
		return DayDiff;
	}
	public void setDayDiff(int dayDiff) {
		DayDiff = dayDiff;
	}
		
}
