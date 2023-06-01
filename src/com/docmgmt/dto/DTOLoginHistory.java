
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOLoginHistory implements Serializable{

		String loginId; 
		int userId;
		int userGroupId;
		String loginRole;
		Timestamp loginOn;
		String userName;
		String userGroupName;
		Timestamp logoutOn;
		String loginIP;
		String userGroupCode;
		
		
	    public String getUserGroupCode() {
			return userGroupCode;
		}
		public void setUserGroupCode(String userGroupCode) {
			this.userGroupCode = userGroupCode;
		}
		/**
	     * @return Returns the loginId.
	     */
	    public String getLoginId() {
	        return loginId;
	    }
	    /**
	     * @param loginId The loginId to set.
	     */
	    public void setLoginId(String loginId) {
	        this.loginId = loginId;
	    }
	    /**
	     * @return Returns the loginOn.
	     */
	 
	    /**
	     * @return Returns the loginRole.
	     */
	    public String getLoginRole() {
	        return loginRole;
	    }
	    /**
	     * @param loginRole The loginRole to set.
	     */
	    public void setLoginRole(String loginRole) {
	        this.loginRole = loginRole;
	    }
	    /**
	     * @return Returns the userGroupId.
	     */
	    public int getUserGroupId() {
	        return userGroupId;
	    }
	    /**
	     * @param userGroupId The userGroupId to set.
	     */
	    public void setUserGroupId(int userGroupId) {
	        this.userGroupId = userGroupId;
	    }
	    /**
	     * @return Returns the userId.
	     */
	    public int getUserId() {
	        return userId;
	    }
	    /**
	     * @param userId The userId to set.
	     */
	    public void setUserId(int userId) {
	        this.userId = userId;
	    }
		/**
		 * @return Returns the userGroupName.
		 */
		public String getUserGroupName() {
			return userGroupName;
		}
		/**
		 * @param userGroupName The userGroupName to set.
		 */
		public void setUserGroupName(String userGroupName) {
			this.userGroupName = userGroupName;
		}
		/**
		 * @return Returns the userName.
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Timestamp getLogoutOn() {
			return logoutOn;
		}
		public void setLogoutOn(Timestamp logoutOn) {
			this.logoutOn = logoutOn;
		}
		public Timestamp getLoginOn() {
			return loginOn;
		}
		public void setLoginOn(Timestamp loginOn) {
			this.loginOn = loginOn;
		}
		public String getLoginIP() {
			return loginIP;
		}
		public void setLoginIP(String loginIP) {
			this.loginIP = loginIP;
		}
}
