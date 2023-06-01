package com.docmgmt.dto;

import java.sql.Timestamp;

public class DTOUserProfile {
	
	int userCode;
	Character profileType;
	String profileSubType;
	String profilevalue;
	Character alertOn;
	String remark;
	int modifyBy;
	Timestamp modifyOn;
	Character statusIndi;
	String userName;
	String loginName;
	Character userMstStatusIndi;
	String userGroupName;
	
	public int getUserCode() {
		return userCode;
	}
	public void setUserCode(int userCode) {
		this.userCode = userCode;
	}
	public Character getProfileType() {
		return profileType;
	}
	public void setProfileType(Character profileType) {
		this.profileType = profileType;
	}
	public String getProfilevalue() {
		return profilevalue;
	}
	public void setProfilevalue(String profilevalue) {
		this.profilevalue = profilevalue;
	}
	public Character getAlertOn() {
		return alertOn;
	}
	public void setAlertOn(Character alertOn) {
		this.alertOn = alertOn;
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
	public Character getStatusIndi() {
		return statusIndi;
	}
	public void setStatusIndi(Character statusIndi) {
		this.statusIndi = statusIndi;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Character getUserMstStatusIndi() {
		return userMstStatusIndi;
	}
	public void setUserMstStatusIndi(Character userMstStatusIndi) {
		this.userMstStatusIndi = userMstStatusIndi;
	}
	public String getProfileSubType() {
		return profileSubType;
	}
	public void setProfileSubType(String profileSubType) {
		this.profileSubType = profileSubType;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public void setUserGroupName(String userGroupName) {
		this.userGroupName = userGroupName;
	}
}
