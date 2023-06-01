
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOUserLoginFailureDetails implements Serializable{

	 	long srNo;
	 	String userName;
	 	Timestamp lastFailedLogin;
		long attemptCount;
		char blockedFlag;
		Timestamp modifyOn;
		char statusIndi;
		int userGroupCode;
		String userTypeCode;
		int modifyBy;
		String loginName;
		String locationCode;
		int userCode;
		String roleCode;
		
		public int getUserCode() {
			return userCode;
		}
		public void setUserCode(int userCode) {
			this.userCode = userCode;
		}
		public int getUserGroupCode() {
			return userGroupCode;
		}
		public void setUserGroupCode(int userGroupCode) {
			this.userGroupCode = userGroupCode;
		}
		public String getUserTypeCode() {
			return userTypeCode;
		}
		public void setUserTypeCode(String userTypeCode) {
			this.userTypeCode = userTypeCode;
		}
		public int getModifyBy() {
			return modifyBy;
		}
		public void setModifyBy(int modifyBy) {
			this.modifyBy = modifyBy;
		}
		public String getLoginName() {
			return loginName;
		}
		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}
		public String getLocationCode() {
			return locationCode;
		}
		public void setLocationCode(String locationCode) {
			this.locationCode = locationCode;
		}
		
		public long getSrNo() {
			return srNo;
		}
		public void setSrNo(long srNo) {
			this.srNo = srNo;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Timestamp getLastFailedLogin() {
			return lastFailedLogin;
		}
		public void setLastFailedLogin(Timestamp lastFailedLogin) {
			this.lastFailedLogin = lastFailedLogin;
		}
		public long getAttemptCount() {
			return attemptCount;
		}
		public void setAttemptCount(long attemptCount) {
			this.attemptCount = attemptCount;
		}
		public char getBlockedFlag() {
			return blockedFlag;
		}
		public void setBlockedFlag(char blockedFlag) {
			this.blockedFlag = blockedFlag;
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
		public String getRoleCode() {
			return roleCode;
		}
		public void setRoleCode(String roleCode) {
			this.roleCode = roleCode;
		}
}
