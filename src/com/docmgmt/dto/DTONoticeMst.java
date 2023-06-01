package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTONoticeMst
{
	int NoticeNo;
	String Subject;
	String Description;
	String Attachment;
	Timestamp Startdate;
	Timestamp EndDate;
	String UserTypeCode;
	String UserTypeCodeNames;
	char IsActive;
	int CreatedBy;
	String UserNameOfCreator;	
	String Remark;	
	int ModifyBy;
	Timestamp ModifyOn;
	
	public String getUserNameOfCreator() {
		return UserNameOfCreator;
	}
	public void setUserNameOfCreator(String userNameOfCreator) {
		UserNameOfCreator = userNameOfCreator;
	}
	public String getUserTypeCodeNames() {
		return UserTypeCodeNames;
	}
	public void setUserTypeCodeNames(String userTypeCodeNames) {
		UserTypeCodeNames = userTypeCodeNames;
	}
	public int getNoticeNo() {
		return NoticeNo;
	}
	public void setNoticeNo(int noticeNo) {
		NoticeNo = noticeNo;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getAttachment() {
		return Attachment;
	}
	public void setAttachment(String attachment) {
		Attachment = attachment;
	}
	public Timestamp getStartdate() {
		return Startdate;
	}
	public void setStartdate(Timestamp startdate) {
		Startdate = startdate;
	}
	public Timestamp getEndDate() {
		return EndDate;
	}
	public void setEndDate(Timestamp endDate) {
		EndDate = endDate;
	}
	public String getUserTypeCode() {
		return UserTypeCode;
	}
	public void setUserTypeCode(String userTypeCode) {
		UserTypeCode = userTypeCode;
	}
	public char getIsActive() {
		return IsActive;
	}
	public void setIsActive(char isActive) {
		IsActive = isActive;
	}
	public int getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(int createdBy) {
		CreatedBy = createdBy;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public int getModifyBy() {
		return ModifyBy;
	}
	public void setModifyBy(int modifyBy) {
		ModifyBy = modifyBy;
	}
	public Timestamp getModifyOn() {
		return ModifyOn;
	}
	public void setModifyOn(Timestamp modifyOn) {
		ModifyOn = modifyOn;
	}	
	
}