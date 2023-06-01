
package com.docmgmt.dto;

import java.io.Serializable;
import java.sql.Timestamp;


public class DTOUserLoginDetails implements Serializable{

	 String userId; 
		Timestamp loginDateTime;
		Timestamp lastActivitydate;
	    /**
	     * @return Returns the loginId.
	     */
	    public String getLoginId() {
	        return userId;
	    }
	    /**
	     * @param loginId The loginId to set.
	     */
	    public void setLoginId(String loginId) {
	        this.userId = loginId;
	    }
	    /**
	     * @return Returns the loginOn.
	     */
		public Timestamp getLoginDateTime() {
			return loginDateTime;
		}
		public void setLoginDateTime(Timestamp loginDateTime) {
			this.loginDateTime = loginDateTime;
		}
		public Timestamp getLastActivitydate() {
			return lastActivitydate;
		}
		public void setLastActivitydate(Timestamp lastActivitydate) {
			this.lastActivitydate = lastActivitydate;
		}
	 
	    /**
	     * @return Returns the loginRole.
	     */
}
